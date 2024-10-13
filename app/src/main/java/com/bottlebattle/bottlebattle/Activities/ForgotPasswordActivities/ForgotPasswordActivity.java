package com.bottlebattle.bottlebattle.Activities.ForgotPasswordActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserRepository;
import com.bottlebattle.bottlebattle.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private TextView recoverPassTV;
    private EditText usernameFP;
    private TextView usernameFP_Feedback;
    private Button submitUsernameBTN;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setUserRepository();
        assignVarsToLayouts();
        setFonts();
        addFunctionalities();
    }

    private void assignVarsToLayouts() {
        recoverPassTV = findViewById(R.id.recoverPassTV);
        usernameFP = findViewById(R.id.usernameFP);
        usernameFP_Feedback = findViewById(R.id.usernameFP_Feedback);
        submitUsernameBTN = findViewById(R.id.submitUsernameBTN);
    }
    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        recoverPassTV.setTypeface(productSansFont);
        usernameFP.setTypeface(productSansFont);
        usernameFP_Feedback.setTypeface(productSansFont);
        submitUsernameBTN.setTypeface(productSansFont);
    }

    private void setUserRepository() {
        String baseServerDomain = getIntent().getStringExtra("baseServerDomain");
        userRepository = new UserRepository(null, baseServerDomain, null); // (still) no token and no username
    }

    private void addFunctionalities() {
       enableAndDisableSubmitBTN();
       submitUsernameBTN.setOnClickListener(v -> checkUsername());
    }
    private void enableAndDisableSubmitBTN() {
        Context context = this;
        usernameFP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                usernameFP_Feedback.setText(""); //clear err message (if any)
                String usernameSTR = usernameFP.getText().toString();
                if (!(usernameSTR.isEmpty())) {
                    submitUsernameBTN.setBackground(ActivityCompat.getDrawable(context, R.drawable.round_button));
                    submitUsernameBTN.setEnabled(true);
                } else {
                    submitUsernameBTN.setBackground(ActivityCompat.getDrawable(context, R.drawable.round_button_disabled));
                    submitUsernameBTN.setEnabled(false);
                }
            }
        });
    }
    private void checkUsername() {
        String usernameSTR = usernameFP.getText().toString();
        userRepository.forgotPassword(this, usernameSTR, usernameFP_Feedback);
    }
}