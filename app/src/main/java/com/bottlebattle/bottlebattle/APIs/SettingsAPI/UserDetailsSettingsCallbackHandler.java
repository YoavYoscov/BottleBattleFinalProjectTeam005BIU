package com.bottlebattle.bottlebattle.APIs.SettingsAPI;

import android.content.Context;
import android.widget.AutoCompleteTextView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.ProfilePicUpdater;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsSettingsCallbackHandler {
    private final Context context;
    private final CircleImageView profilePicIV;
    private final AutoCompleteTextView newCityACTV;

    public UserDetailsSettingsCallbackHandler(Context context, CircleImageView profilePic, AutoCompleteTextView newCityACTV) {
        this.context = context;
        this.profilePicIV = profilePic;
        this.newCityACTV = newCityACTV;
    }

    public void updateUserDetails(UserDetailsDTO userDetails) {
        updateProfilePic(userDetails.getProfilePic());
        newCityACTV.setText(userDetails.getCity());
    }

    public void updateProfilePic(String profilePicRes) {
        ProfilePicUpdater profilePicUpdater = new ProfilePicUpdater();
        profilePicUpdater.updateProfilePic(context, profilePicRes, profilePicIV);
    }
}
