package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bottlebattle.bottlebattle.R;


public class AddFriendByUsernameCallbackHandler {
    private final Context context;
    private final TextView addFriendsFeedback;
    private final AutoCompleteTextView addFriendsACTV;

    public AddFriendByUsernameCallbackHandler(Context context, TextView addFriendsFeedback, AutoCompleteTextView addFriendsACTV) {
        this.addFriendsFeedback = addFriendsFeedback;
        this.context = context;
        this.addFriendsACTV = addFriendsACTV;
    }

    public void onSuccess(String friendUsername) {
        addFriendsACTV.setText(""); //clear text field when successfully adding a friend
        String successFeedback = "You and " + friendUsername + " are now friends";
        int successColor = ActivityCompat.getColor(context, R.color.success_feedback);
        addFriendsFeedback.setTextColor(successColor);
        addFriendsFeedback.setText(successFeedback);
    }

    public void onError(int resStatus, String friendUsername) {
        int errorColor = ActivityCompat.getColor(context, R.color.error_feedback);
        String errorFeedback;
        if (resStatus == 409) errorFeedback = "You and " + friendUsername + " are already friends";
        else errorFeedback = context.getString(R.string.an_error_occurred_please_try_again_later);
        addFriendsFeedback.setTextColor(errorColor);
        addFriendsFeedback.setText(errorFeedback);
    }
}
