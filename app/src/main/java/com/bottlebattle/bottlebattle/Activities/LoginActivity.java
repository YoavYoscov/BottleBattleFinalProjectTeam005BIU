package com.bottlebattle.bottlebattle.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.bottlebattle.bottlebattle.APIs.TokenAPI.DefaultLoginRequestDTO;
import com.bottlebattle.bottlebattle.APIs.TokenAPI.TokenRepository;
import com.bottlebattle.bottlebattle.Activities.ForgotPasswordActivities.ForgotPasswordActivity;
import com.bottlebattle.bottlebattle.R;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private TextView welcomeMsg;
    private TextView feedback;
    private EditText username;
    private EditText password;
    private Button forgotPasswordBtn;
    private Button signInBtn;
    private Button googleBtn;
    private TextView registerTxt;
    private Button registerBtn;
    private final String baseServerDomain = "https://bottlebattle.onrender.com/";
    // In case you want to test the app on a local server, use the following baseServerDomain, changing [YOUR_IP_ADDR] to your local IP
    // (you can find it in the IPv4 Address field of the output of the command 'ipconfig' in cmd on Windows, or the command 'ifconfig' on Linux).
    // private final String baseServerDomain = "http://[YOUR_IP_ADDR]:5000";


    private final TokenRepository tokenRepository = new TokenRepository(baseServerDomain);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            TimeUnit.MILLISECONDS.sleep(300); //display splash screen for 0.3 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assignVarsToLayouts();
        setFonts();
        setClickListeners();

        // ADDED TO UPDATE FROM FIREBASE LEGACY:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // We still do not generate the Firebase Token, since the user hasn't logged-in yet.
        // However we should ask the user for notification permission in advance, so that when we send it to him - he'll get it.
        askNotificationPermission();
    }

    private void assignVarsToLayouts() {
        welcomeMsg = findViewById(R.id.welcomeMsg);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        signInBtn = findViewById(R.id.signInBtn);
        googleBtn = findViewById(R.id.googleBtn);
        registerTxt = findViewById(R.id.registerTxt);
        registerBtn = findViewById(R.id.registerBtn);
        feedback = findViewById(R.id.feedback);
    }

    private void setFonts() {
        Typeface welcomeFont = Typeface.createFromAsset(getAssets(), "fonts/Writer.otf");
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        signInBtn.setTypeface(productSansFont);
        username.setTypeface(productSansFont);
        password.setTypeface(productSansFont);
        forgotPasswordBtn.setTypeface(productSansFont);
        googleBtn.setTypeface(productSansFont);
        registerTxt.setTypeface(productSansFont);
        registerBtn.setTypeface(productSansFont);
        welcomeMsg.setTypeface(welcomeFont);
    }

    private void setClickListeners() {
        setClickListenerForRegistration();
        setClickListenerForGoogleSignIn();
        setClickListenerForDefaultSignIn();
        setClickListenerForForgotPassword();
    }

    private void setClickListenerForRegistration() {
        registerBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, RegistrationActivity.class);
            i.putExtra("baseServerDomain", baseServerDomain);
            startActivity(i);
        });
    }

    /*based on google's official instructions for integrating their credential manager:
    https://developer.android.com/training/sign-in/credential-manager#java */
    private void setClickListenerForGoogleSignIn() {
        googleBtn.setOnClickListener(v -> {
            //web_client_id was generated by setting up a Google API console project on https://console.cloud.google.com/apis:
            GetSignInWithGoogleOption signInWithGoogleOption = new GetSignInWithGoogleOption(getString(R.string.web_client_id), null, null);
            //retrieve user's credentials (using the retrieved Google ID token):
            GetCredentialRequest request = new GetCredentialRequest.Builder().addCredentialOption(signInWithGoogleOption).build();
            CredentialManager credentialManager = CredentialManager.create(this);
            CancellationSignal cancellationSignal = new CancellationSignal();
            Executor executor = ActivityCompat.getMainExecutor(this); //handle result on main thread (still, the handle method can create new threads of its own)
            credentialManager.getCredentialAsync(this, request, cancellationSignal, executor, new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                @Override
                public void onResult(GetCredentialResponse result) {
                    handleGoogleSignIn(result);
                }

                @Override
                public void onError(@NonNull GetCredentialException e) {
                    //do nothing if user fails signing-in to his/her google account
                }
            });
        });
    }

    /*based on google's official instructions for integrating their credential manager:
     https://developer.android.com/training/sign-in/credential-manager#java */
    private void handleGoogleSignIn(GetCredentialResponse result) {
        // Handle the successfully returned credential.
        Credential credential = result.getCredential();
        if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
            // Use googleIdTokenCredential and extract id to validate and
            // authenticate on your server
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
            generateFireBaseToken(googleIdTokenCredential);

        } else {
            Log.e("CredentialsTypeError", "credential not of type GoogleIdTokenCredential");
        }
    }

    private void setClickListenerForDefaultSignIn() {
        signInBtn.setOnClickListener(v -> {
            //first, check if one of the fields is empty:
            if (isEmpty(username) || isEmpty(password)) {
                feedback.setText(R.string.please_provide_credentials);
                return;
            } //if username & password aren't empty, try to sign-in by sending login req to server:
            String usernameSTR = username.getText().toString(), passwordSTR = password.getText().toString();
            generateFireBaseToken(usernameSTR, passwordSTR);

        });
    }

    private boolean isEmpty(EditText et) { //checks whether the text in an EditText object is empty
        return et.getText().toString().isEmpty();
    }

    private void setClickListenerForForgotPassword() {
        forgotPasswordBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, ForgotPasswordActivity.class);
            i.putExtra("baseServerDomain", baseServerDomain);
            startActivity(i);
        });
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // This means that the user has granted the permission!

                } else {
                    // This means that the user has not granted the permission.
                    // We may choose to explain the user trying to convince him...
                    Toast.makeText(this, "Notification permission not granted", Toast.LENGTH_SHORT).show();
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU), since notification permission is granted BY DEFAULT on lower API levels:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // We check if the user has already granted the permission:
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // This means that the user has already granted the permission!
                // (Nothing to be done further).
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // This means that the user has previously denied the permission, so will ask for it again!
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);

                // We can explain to the user why we need this permission (if we want to for some reason in the future)...
                // Not relevant for now.
            } else {
                // We ask the user for the permission:
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }

    }

    // This method is called when the user signs in with the default login (NOT Google Sign-In):
    private void generateFireBaseToken(String usernameSTR, String passwordSTR) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    Log.d("TOKEN", "FCM token: " + token);
                    DefaultLoginRequestDTO loginRequest = new DefaultLoginRequestDTO(usernameSTR, passwordSTR, token);
                    tokenRepository.defaultLogin(loginRequest, this, feedback);
                });
    }

    // This method is called when the user signs in with Google:
    private void generateFireBaseToken(GoogleIdTokenCredential googleIdTokenCredential) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String fireBaseToken = task.getResult();
                    tokenRepository.checkIfGoogleUserExistsAndSignIn(googleIdTokenCredential, fireBaseToken, this);
                });

    }
}