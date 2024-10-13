package com.bottlebattle.bottlebattle.APIs.TokenAPI;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

public class TokenRepository {
    private final TokenAPI tokenAPI;
    private final String baseServerDomain;

    public TokenRepository(String baseServerDomain) {
        this.baseServerDomain = baseServerDomain;
        this.tokenAPI = new TokenAPI(baseServerDomain);
    }

    public void defaultLogin(DefaultLoginRequestDTO loginRequest, Context context, TextView feedback) {
        DefaultLoginCallbackHandler callbackHandler =
                new DefaultLoginCallbackHandler(context, feedback);
        tokenAPI.defaultLogin(loginRequest, callbackHandler);
    }

    public void checkIfGoogleUserExistsAndSignIn(GoogleIdTokenCredential googleIdTokenCredential, String fireBaseToken, Context context) {
        //String username = googleIdTokenCredential.getId();
        // If the username is actually an email, we take the part before the '@' symbol:
        String username = googleIdTokenCredential.getId().split("@")[0];
        //Toast.makeText(context, "username: " + username, Toast.LENGTH_SHORT).show();
        String googleIdToken = googleIdTokenCredential.getIdToken();
        String profilePic;
        if (googleIdTokenCredential.getProfilePictureUri() != null) {
            profilePic = googleIdTokenCredential.getProfilePictureUri().toString();
        } else {
            profilePic = "none";
        }
        CheckIfGoogleUserExistsCallbackHandler callbackHandler =
                new CheckIfGoogleUserExistsCallbackHandler(context, username, googleIdToken, profilePic, fireBaseToken, baseServerDomain);
        tokenAPI.checkIfGoogleUserExistsAndSignIn(username, callbackHandler);
    }

    public void googleSignIn(GoogleSignInRequestDTO googleSignInRequest, String username, Context context) {
        GoogleSignInCallbackHandler callbackHandler =
                new GoogleSignInCallbackHandler(context, username, baseServerDomain);
        tokenAPI.googleSignIn(googleSignInRequest, callbackHandler); //sign user in and display home screen
    }
}
