package com.bottlebattle.bottlebattle.APIs.UserAPI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.Activities.ForgotPasswordActivities.ResetPasswordActivity;

public class ForgotPasswordCallbackHandler {
    private final Context context;
    private final TextView usernameFP_Feedback;

    public ForgotPasswordCallbackHandler(Context context, TextView usernameFP_Feedback) {
        this.context = context;
        this.usernameFP_Feedback = usernameFP_Feedback;
    }

    public void onSuccess(String username, String baseServerDomain) {
        //email sent successfully => proceed to reset password screen
        //username "taken" => exists, hence can go on in the password recovery process
        usernameFP_Feedback.setText("");
        Intent i = new Intent(context, ResetPasswordActivity.class);
        i.putExtra("username", username);
        i.putExtra("baseServerDomain", baseServerDomain);
        context.startActivity(i);
        ((Activity) context).finish();
    }

    public void onError(String errorMessage) {
        //some error occurred (username not found, etc.), => display server's err message
        usernameFP_Feedback.setText(errorMessage); //display err message on screen
    }
}
