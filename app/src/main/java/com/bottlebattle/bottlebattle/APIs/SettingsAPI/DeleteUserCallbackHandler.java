package com.bottlebattle.bottlebattle.APIs.SettingsAPI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.bottlebattle.bottlebattle.Activities.LoginActivity;
import com.bottlebattle.bottlebattle.R;

public class DeleteUserCallbackHandler {
    private final Context context;
    private final TextView deleteUserFeedback;

    public DeleteUserCallbackHandler(Context context, TextView deleteUserFeedback) {
        this.context = context;
        this.deleteUserFeedback = deleteUserFeedback;
    }

    public void onSuccess() {
        Toast.makeText(context.getApplicationContext(), "Account deleted successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void onErr() {
        this.deleteUserFeedback.setText(R.string.an_error_occurred_please_try_again_later);
    }
}
