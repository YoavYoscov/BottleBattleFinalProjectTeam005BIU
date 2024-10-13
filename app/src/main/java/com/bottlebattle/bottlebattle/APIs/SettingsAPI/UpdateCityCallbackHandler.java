package com.bottlebattle.bottlebattle.APIs.SettingsAPI;

import android.content.Context;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bottlebattle.bottlebattle.R;

public class UpdateCityCallbackHandler {
    private final Context context;
    private final TextView newCityFeedback;

    public UpdateCityCallbackHandler(Context context, TextView newCityFeedback) {
        this.context = context;
        this.newCityFeedback = newCityFeedback;
    }

    public void onSuccess() {
        int successColor = ActivityCompat.getColor(context, R.color.success_feedback);
        this.newCityFeedback.setTextColor(successColor);
        this.newCityFeedback.setText(R.string.city_update_success);
    }

    public void onErr() {
        int errorColor = ActivityCompat.getColor(context, R.color.error_feedback);
        this.newCityFeedback.setTextColor(errorColor);
        this.newCityFeedback.setText(R.string.an_error_occurred_please_try_again_later);
    }
}
