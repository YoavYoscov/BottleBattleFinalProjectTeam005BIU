package com.bottlebattle.bottlebattle.APIs.UserAPI;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class ResetPasswordCallbackHandler {
    private final Context context;
    private final TextView tokenFeedback;
    private final AtomicBoolean isFormValid;

    public ResetPasswordCallbackHandler(Context context, TextView tokenFeedback, AtomicBoolean isFormValid) {
        this.context = context;
        this.tokenFeedback = tokenFeedback;
        this.isFormValid = isFormValid;
    }

    public void onSuccess() {
        //get back to login:
        ((Activity) context).finish();
    }

    public void onError(String errorMessage) {
        tokenFeedback.setText(errorMessage); //display err message on screen
        isFormValid.set(false);
    }
}
