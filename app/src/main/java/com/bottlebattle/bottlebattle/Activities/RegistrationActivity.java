package com.bottlebattle.bottlebattle.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.CreateUserReqDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserRepository;
import com.bottlebattle.bottlebattle.APIs.citiesAPI.CitiesRepository;
import com.bottlebattle.bottlebattle.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegistrationActivity extends AppCompatActivity {
    private TextView accountInfoTxt;
    private EditText username;
    private TextView usernameFeedback;
    private final AtomicBoolean usernameValid = new AtomicBoolean(false);
    private EditText password;
    private TextView passwordFeedback;
    private boolean passwordValid = false;
    private EditText confirmPassword;
    private TextView confirmPasswordFeedback;
    private boolean confirmPasswordValid = false;
    private EditText email;
    private TextView emailFeedback;
    private boolean emailValid = false;
    private TextView locationTxt;
    private CitiesRepository citiesRepository;
    private AutoCompleteTextView city;
    private TextView cityFeedback;
    private boolean cityValid = false;
    private TextView profilePicTxt;
    private CircleImageView profilePic;
    private TextView profilePicFeedback;
    private Button submitButton;
    private TextView createUserFeedback;
    private String fireBaseToken;
    private Bitmap profilePicBitmap = null;
    private String profilePicBase64 = "none";
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        assignVarsToLayouts();
        setFonts();
        setUserRepository();
        generateFireBaseToken();
        addFunctionalities();
    }

    private void assignVarsToLayouts() {
        accountInfoTxt = findViewById(R.id.accountInfoTxt);
        username = findViewById(R.id.username);
        usernameFeedback = findViewById(R.id.usernameFeedback);
        password = findViewById(R.id.password);
        passwordFeedback = findViewById(R.id.passwordFeedback);
        confirmPassword = findViewById(R.id.confirmPassword);
        confirmPasswordFeedback = findViewById(R.id.confirmPasswordFeedback);
        email = findViewById(R.id.email);
        emailFeedback = findViewById(R.id.emailFeedback);
        locationTxt = findViewById(R.id.locationTxt);
        city = findViewById(R.id.city);
        cityFeedback = findViewById(R.id.cityFeedback);
        profilePicTxt = findViewById(R.id.profilePicTxt);
        profilePic = findViewById(R.id.profilePic);
        profilePicFeedback = findViewById(R.id.profilePicFeedback);
        submitButton = findViewById(R.id.submitButton);
        createUserFeedback = findViewById(R.id.createUserFeedback);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        accountInfoTxt.setTypeface(productSansFont);
        username.setTypeface(productSansFont);
        usernameFeedback.setTypeface(productSansFont);
        password.setTypeface(productSansFont);
        passwordFeedback.setTypeface(productSansFont);
        confirmPassword.setTypeface(productSansFont);
        confirmPasswordFeedback.setTypeface(productSansFont);
        email.setTypeface(productSansFont);
        emailFeedback.setTypeface(productSansFont);
        locationTxt.setTypeface(productSansFont);
        city.setTypeface(productSansFont);
        cityFeedback.setTypeface(productSansFont);
        profilePicTxt.setTypeface(productSansFont);
        profilePicFeedback.setTypeface(productSansFont);
        submitButton.setTypeface(productSansFont);
        createUserFeedback.setTypeface(productSansFont);
    }

    private void setUserRepository() {
        String baseServerDomain = getIntent().getStringExtra("baseServerDomain");
        userRepository = new UserRepository(null, baseServerDomain, null); //(still) no token or username
    }

    private void generateFireBaseToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> fireBaseToken = task.getResult());
    }

    private void addFunctionalities() {
        addTextChangedListeners(); //dynamically display/hide error messages while user is typing
        setOnFocusChangeListeners(); //display/hide error messages when user finished editing a certain field
        setCityDropDownList();
        enableImageUpload();
        enableSubmission();
    }

    private void addTextChangedListeners() {
        addTextChangedListener(username, usernameFeedback); //clear feedback error when changed
        addTextChangedListener(password, passwordFeedback); //clear feedback error when changed
        addTextChangedListener(city, cityFeedback); //clear feedback error when changed
        addTextChangedListener(email, emailFeedback); //clear feedback error when changed
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordFeedback.setText(""); //clear feedback error when changed
                //clear password mismatch error if passwords match:
                if (confirmPasswordFeedback.getText().toString().equals("Passwords do not match")) {
                    checkForPasswordMismatch();
                }
            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // if error message is due to empty field:
                if (confirmPasswordFeedback.getText().toString().equals("Please confirm your password")) {
                    confirmPasswordFeedback.setText(""); //clear feedback error when changed
                    //else if error message is due to mismatch with original password, or no error message:
                    //display mismatch error only if confirmPassword isn't a prefix of original
                } else if (!isEmpty(password)) {
                    if (!isConfirmPrefix())
                        confirmPasswordFeedback.setText(R.string.password_mismatch); //display err msg
                    else confirmPasswordFeedback.setText(""); //clear err msg
                }
            }
        });
    }

    private void addTextChangedListener(EditText et, TextView tv) {
        //clears feedback error (in the TextView tv) when a change in the EditText et is made:
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv.setText("");
            }
        });

    }

    private void setOnFocusChangeListeners() {
        username.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { //when user finishes editing the username field
                if (!isEmpty(username)) {
                    userRepository.checkAvailableUsername(username.getText().toString(), usernameValid, usernameFeedback);
                }
            }
        });
        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { //when user finishes editing the password field
                //if both password and confirmPassword aren't empty, check for mismatch:
                if (!isEmpty(password) && !isEmpty(confirmPassword)) checkForPasswordMismatch();
            }
        });
        confirmPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { //when user finishes editing the confirmPassword field
                //if both password and confirmPassword aren't empty, check for mismatch:
                if (!isEmpty(password) && !isEmpty(confirmPassword)) checkForPasswordMismatch();
            }
        });
        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { //when user finishes editing the email field:
                //if email isn't empty and isn't valid, display error message:
                if (!isEmpty(email) && !isValidEmail(email.getText().toString())) {
                    emailFeedback.setText(R.string.wrong_email);
                }
            }
        });
        city.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { //when user finishes editing the city field
                if (!isCurrentCityValid() && !isEmpty(city)) {
                    cityFeedback.setText(R.string.empty_city); //display err message
                }
            }
        });
    }

    private void setCityDropDownList() {
        citiesRepository = new CitiesRepository();
        citiesRepository.getCitiesInIsrael( this, city);
    }

    private void enableImageUpload() {
        //the ActivityResultLauncher is used to get the result (image chosen) from the activity which is about to be invoked (the image selection screen)
        //its 2nd argument is the callback function to be called after the image selection activity is chosen
        ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::imagePickerCallBack);
        profilePic.setOnClickListener(v -> {
            city.clearFocus(); //clears focus on the city field to invoke its onFocusChanged method
            // (other text fields have another text fields after them, hence it happens automatically for them).
            pickImageLauncher.launch("image/*"); //launch the image selection activity
        });
    }

    private void imagePickerCallBack(android.net.Uri uri) { //callback function after image has been selected
        //uri is the reference to the image chosen
        if (uri != null) {
            try {
                //get binary representation of the image chosen, and set it to be the source of the ImageButton:
                profilePicBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profilePic.setImageBitmap(profilePicBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setProfilePicBase64(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            profilePicBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    private void enableSubmission() {
        //when clicking on the submit button:
        submitButton.setOnClickListener(v -> {
            //the following checks are invoked only on submission, hence display error messages also on empty fields:
            checkUsername();
            checkPassword();
            checkConfirmPassword();
            checkEmail();
            checkCity();
            if (areAllValid()) {
                sendDataToServer(); //note that this method also closes current activity
            }
        });
    }

    private void sendDataToServer() {
        setProfilePicBase64(profilePicBitmap); //sets profilePicBase64 with the b64 of chosen image (can also be default one)
        String usernameStr = this.username.getText().toString(),
                passwordStr = this.password.getText().toString(),
                cityStr = this.city.getText().toString(),
                emailStr = this.email.getText().toString();
        CreateUserReqDTO newUser = new CreateUserReqDTO(usernameStr, passwordStr, profilePicBase64, fireBaseToken, cityStr, emailStr);
        userRepository.createUser(newUser, this, usernameValid, createUserFeedback);
    }

    private void checkUsername() {
        if (isEmpty(username)) { //if user name is empty:
            usernameFeedback.setText(R.string.username_cannot_be_empty);
            usernameValid.set(false);
        } else { //otherwise, check if it is available via an api request to the server:
            userRepository.checkAvailableUsername(username.getText().toString(), usernameValid, usernameFeedback);
        }
    }

    private void checkPassword() {
        if (isEmpty(password)) { //if password is empty:
            passwordFeedback.setText(R.string.password_cannot_be_empty);
            confirmPasswordFeedback.setText("");
            passwordValid = false;
        } else { //otherwise, it is valid (no other requirements are currently enforced for users' passwords)
            passwordValid = true;
        }
    }

    private void checkConfirmPassword() {
        if (isEmpty(confirmPassword)) { //if empty on submission, display error message anyway:
            confirmPasswordFeedback.setText(R.string.please_confirm);
            confirmPasswordValid = false;
        } else if (!isEmpty(password)) { // if not, display mismatch error (if any) only if password isn't empty as well:
            checkForPasswordMismatch();
        }
    }

    private void checkEmail() {
        if (isEmpty(email)) {
            emailFeedback.setText(R.string.email_cannot_be_empty);
            emailValid = false; //empty
        } else {
            if (!isValidEmail(email.getText().toString())) {
                emailFeedback.setText(R.string.wrong_email);
                emailValid = false; //not empty & not valid
            } else {
                emailValid = true; //not empty & valid
            }
        }
    }

    private void checkCity() {
        if (!isCurrentCityValid()) {
            cityFeedback.setText(R.string.empty_city); //including the check whether the field is empty
            cityValid = false;
        } else {
            cityValid = true;
        }
    }

    private boolean isCurrentCityValid() {
        //current value in the city field is valid only if it is contained in the retrieved cities list
        return citiesRepository.getCities().contains(city.getText().toString());
    }

    private boolean areAllValid() {
        //if true, user has successfully registered
        return usernameValid.get() && passwordValid && confirmPasswordValid && cityValid && emailValid;
    }

    private boolean isEmpty(EditText et) { //checks whether the text in an EditText object is empty
        return et.getText().toString().isEmpty();
    }

    private void checkForPasswordMismatch() {
        String currPassword = password.getText().toString();
        String currConfirmPassword = confirmPassword.getText().toString();
        //if password matches confirmPassword, no error feedback for confirmPassword is needed:
        if (currPassword.equals(currConfirmPassword)) {
            confirmPasswordFeedback.setText("");
            confirmPasswordValid = true;
        } else { //else, display err message and mark confirmPassword as invalid
            confirmPasswordFeedback.setText(R.string.password_mismatch);
            confirmPasswordValid = false;
        }
    }

    private boolean isConfirmPrefix() { //returns true iff confirmPassword is a prefix of password
        String currPass = password.getText().toString(), currConfirm = confirmPassword.getText().toString();
        return currPass.startsWith(currConfirm);
    }

    private boolean isValidEmail(String email) {
        //check that the given email is valid using regex:
        String emailRegex =
                "^\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}