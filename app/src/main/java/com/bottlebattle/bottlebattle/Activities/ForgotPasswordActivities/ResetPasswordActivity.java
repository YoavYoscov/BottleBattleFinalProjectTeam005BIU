package com.bottlebattle.bottlebattle.Activities.ForgotPasswordActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.ResetPasswordReqDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserRepository;
import com.bottlebattle.bottlebattle.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class ResetPasswordActivity extends AppCompatActivity {
    private String username;
    private UserRepository userRepository;
    private TextView passwordResetTV;
    private LinearLayout tokens;
    private EditText token1;
    private EditText token2;
    private EditText token3;
    private EditText token4;
    private EditText token5;
    private EditText token6;
    private TextView tokenFeedback;
    private EditText newPassword;
    private TextView newPasswordFeedback;
    private Button submitUsernameBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        retrieveUsername();
        setUserRepository();
        assignVarsToLayouts();
        setFonts();
        addFunctionalities();
    }

    private void retrieveUsername() {
        username = getIntent().getStringExtra("username");
    }

    private void setUserRepository() {
        String baseServerDomain = getIntent().getStringExtra("baseServerDomain");
        userRepository = new UserRepository(null, baseServerDomain, null);
    }

    private void assignVarsToLayouts() {
        passwordResetTV = findViewById(R.id.passwordResetTV);
        tokens = findViewById(R.id.tokens);
        token1 = findViewById(R.id.token1);
        token2 = findViewById(R.id.token2);
        token3 = findViewById(R.id.token3);
        token4 = findViewById(R.id.token4);
        token5 = findViewById(R.id.token5);
        token6 = findViewById(R.id.token6);
        tokenFeedback = findViewById(R.id.tokenFeedback);
        newPassword = findViewById(R.id.newPassword);
        newPasswordFeedback = findViewById(R.id.newPasswordFeedback);
        submitUsernameBTN = findViewById(R.id.submitUsernameBTN);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        passwordResetTV.setTypeface(productSansFont);
        token1.setTypeface(productSansFont);
        token2.setTypeface(productSansFont);
        token3.setTypeface(productSansFont);
        token4.setTypeface(productSansFont);
        token5.setTypeface(productSansFont);
        token6.setTypeface(productSansFont);
        tokenFeedback.setTypeface(productSansFont);
        newPassword.setTypeface(productSansFont);
        newPasswordFeedback.setTypeface(productSansFont);
        submitUsernameBTN.setTypeface(productSansFont);
    }

    private void addFunctionalities() {
        createTokenFieldsFocusChanges();
        enableSubmission();
    }

    private void createTokenFieldsFocusChanges() {
        addKeyListenerForTokenFields(token1, 0);
        addKeyListenerForTokenFields(token2, 1);
        addKeyListenerForTokenFields(token3, 2);
        addKeyListenerForTokenFields(token4, 3);
        addKeyListenerForTokenFields(token5, 4);
        addKeyListenerForTokenFields(token6, 5);

    }

    private void addKeyListenerForTokenFields(EditText tokenField, int fieldIndex) {
        tokenField.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && fieldIndex > 0
                    && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                //move focus to previous token field:
                tokens.getChildAt(fieldIndex).clearFocus();
                tokens.getChildAt(fieldIndex - 1).requestFocus();
            }
            else if (Character.isLetterOrDigit(keyEvent.getUnicodeChar()) && fieldIndex < 5
                    && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                //move focus to next token field:
                tokens.getChildAt(fieldIndex).clearFocus();
                tokens.getChildAt(fieldIndex + 1).requestFocus();
            }
            return false;
        });
    }
    private void enableSubmission() {
        submitUsernameBTN.setOnClickListener(v -> {
            AtomicBoolean isFormValid = new AtomicBoolean(true);
            if (newPassword.getText().toString().isEmpty()) {
                newPasswordFeedback.setText(R.string.new_pass_required_message);
                isFormValid.set(false);
            } else {
                newPasswordFeedback.setText("");
            }
            if (!tokenFilled()) {
                tokenFeedback.setText(R.string.token_required_message);
                isFormValid.set(false);
            } else {
                tokenFeedback.setText("");
            }
            if (isFormValid.get()) {
                //initiate password reset request:
                String token = getInputToken(), newPasswordSTR = newPassword.getText().toString();
                ResetPasswordReqDTO resetPasswordReq = new ResetPasswordReqDTO(username, token, newPasswordSTR);
                userRepository.resetPassword(this, resetPasswordReq, tokenFeedback, isFormValid);
            }
        });
    }
    private boolean tokenFilled() {
        for (int i = 0; i < 6; i++) {
            if (((EditText) tokens.getChildAt(i)).getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String getInputToken() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            token.append(((EditText) tokens.getChildAt(i)).getText().toString());
        }
        return token.toString();
    }
}