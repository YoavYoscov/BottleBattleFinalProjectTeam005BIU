package com.bottlebattle.bottlebattle.APIs.SettingsAPI;
import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsRepository {
    private final SettingsAPI settingsAPI;
    private final String username;

    public SettingsRepository(String baseServerAddress, String token, String username) {
        this.settingsAPI = new SettingsAPI(baseServerAddress, token);
        this.username = username;
    }

    public void retrieveUserDetails(Context context, CircleImageView profilePic, AutoCompleteTextView newCityACTV) {
        UserDetailsSettingsCallbackHandler callbackHandler =
                new UserDetailsSettingsCallbackHandler(context, profilePic, newCityACTV);
        settingsAPI.getUserByUsername(username, callbackHandler);
    }

    public void updateUserCity(Context context, String city, TextView newCityFeedback) {
        UpdateCityCallbackHandler callbackHandler = new UpdateCityCallbackHandler(context, newCityFeedback);
        settingsAPI.updateUserCity(username, city, callbackHandler);
    }

    public void updateUserProfilePic(Context context, String profilePic, TextView profilePicFeedback) {
        UpdateProfilePicCallbackHandler callbackHandler =
                new UpdateProfilePicCallbackHandler(context, profilePicFeedback);
        settingsAPI.updateUserProfilePic(username, profilePic, callbackHandler);
    }

    public void deleteUser(Context context, TextView deleteUserFeedback) {
        DeleteUserCallbackHandler callbackHandler =
                new DeleteUserCallbackHandler(context, deleteUserFeedback);
        settingsAPI.deleteUser(username, callbackHandler);
    }
}
