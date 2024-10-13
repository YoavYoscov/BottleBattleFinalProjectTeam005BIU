package com.bottlebattle.bottlebattle.APIs.UserAPI;

import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class CheckAvailableUsernameCallbackHandler {
    private final TextView usernameFeedback;
    private final AtomicBoolean isUsernameValid;

    public CheckAvailableUsernameCallbackHandler(TextView usernameFeedback, AtomicBoolean isUsernameValid) {
        this.usernameFeedback = usernameFeedback;
        this.isUsernameValid = isUsernameValid;
    }

    public void onSuccess() {
        //if here, username is available (hence no error message is needed, and username is valid):
        usernameFeedback.setText("");
        isUsernameValid.set(true);
    }
    public void onError(String errorMessage) {
        usernameFeedback.setText(errorMessage);
        isUsernameValid.set(false);
    }
}
