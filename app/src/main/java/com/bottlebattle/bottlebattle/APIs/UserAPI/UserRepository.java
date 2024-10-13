package com.bottlebattle.bottlebattle.APIs.UserAPI;

import android.content.Context;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRepository {
    private final UserAPI userAPI;
    private final String token;
    private final String username;

    public UserRepository(String token, String baseServerDomain, String username) {
        this.token = token;
        this.userAPI = new UserAPI(baseServerDomain);
        this.username = username;
    }

    public void retrieveUserDetails(Context context, TextView moneyAmount, CircleImageView profilePic,
                                    TextView tableLevelValue, TextView tablePointsValue){
        UserDetailsCallbackHandler userDetailsCallbackHandler = new UserDetailsCallbackHandler(context, moneyAmount, profilePic, tableLevelValue, tablePointsValue);
        userAPI.retrieveUserDetails(token, username, userDetailsCallbackHandler);
    }

    public void forgotPassword(Context context, String username, TextView usernameFP_Feedback) {
        ForgotPasswordCallbackHandler callbackHandler =
                new ForgotPasswordCallbackHandler(context, usernameFP_Feedback);
        userAPI.forgotPassword(username, callbackHandler);
    }

    public void checkAvailableUsername(String username, AtomicBoolean usernameValid, TextView usernameFeedback) {
        CheckAvailableUsernameCallbackHandler callbackHandler =
                new CheckAvailableUsernameCallbackHandler(usernameFeedback, usernameValid);
        userAPI.checkAvailableUsername(username, callbackHandler);
    }

    public void createUser(CreateUserReqDTO newUser, Context context, AtomicBoolean usernameValid, TextView usernameFeedback) {
        CreateUserCallbackHandler callbackHandler =
                new CreateUserCallbackHandler(context, usernameFeedback, usernameValid);
        userAPI.createUser(newUser, callbackHandler);
    }

    public void resetPassword(Context context, ResetPasswordReqDTO resetPasswordReq, TextView tokenFeedback, AtomicBoolean isFormValid) {
        ResetPasswordCallbackHandler callbackHandler =
                new ResetPasswordCallbackHandler(context, tokenFeedback, isFormValid);
        userAPI.resetPassword(resetPasswordReq, callbackHandler);
    }
}
