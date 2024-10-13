package com.bottlebattle.bottlebattle.APIs.SettingsAPI;

import android.content.Context;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bottlebattle.bottlebattle.R;

public class UpdateProfilePicCallbackHandler {
    private final Context context;
    private final TextView profilePicFeedback;

    public UpdateProfilePicCallbackHandler(Context context, TextView profilePicFeedback) {
        this.context = context;
        this.profilePicFeedback = profilePicFeedback;
    }

    public void onSuccess() {
        int successColor = ActivityCompat.getColor(context, R.color.success_feedback);
        this.profilePicFeedback.setTextColor(successColor);
        this.profilePicFeedback.setText(R.string.profilePic_update_success);
    }

    public void onErr() {
        int errorColor = ActivityCompat.getColor(context, R.color.error_feedback);
        this.profilePicFeedback.setTextColor(errorColor);
        this.profilePicFeedback.setText(R.string.an_error_occurred_please_try_again_later);
    }
}
