package com.bottlebattle.bottlebattle.APIs.TokenAPI;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.Activities.HomeScreenActivity.HomeScreenActivity;

public class DefaultLoginCallbackHandler {
    private final Context context;
    private final TextView feedback;

    public DefaultLoginCallbackHandler(Context context, TextView feedback) {
        this.context = context;
        this.feedback = feedback;
    }

    public void onSuccess(String token, String username, String baseServerDomain) {
        feedback.setText(""); //clear error message (if any)
        Intent i = new Intent(context, HomeScreenActivity.class);
        i.putExtra("token", token); //jwt token
        i.putExtra("username", username);
        i.putExtra("baseServerDomain", baseServerDomain);
        context.startActivity(i); //display homescreen
    }

    public void onError(String errorMessage) {
        feedback.setText(errorMessage); //display err message on screen
    }
}
