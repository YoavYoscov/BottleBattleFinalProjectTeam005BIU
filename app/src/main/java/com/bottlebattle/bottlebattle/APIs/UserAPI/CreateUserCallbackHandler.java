package com.bottlebattle.bottlebattle.APIs.UserAPI;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class CreateUserCallbackHandler {
    private final Context context;
    private final TextView usernameFeedback;
    private final AtomicBoolean isUsernameValid;

    public CreateUserCallbackHandler(Context context, TextView usernameFeedback, AtomicBoolean isUsernameValid) {
        this.context = context;
        this.usernameFeedback = usernameFeedback;
        this.isUsernameValid = isUsernameValid;
    }

    public void onSuccess() {
        ((Activity) context).finish();
    }
    public void onError(String errorMessage) {
        usernameFeedback.setText(errorMessage);
        isUsernameValid.set(false);
    }
}
