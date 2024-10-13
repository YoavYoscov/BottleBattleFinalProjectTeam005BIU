package com.bottlebattle.bottlebattle.APIs.TokenAPI;

import android.content.Context;
import android.content.Intent;

import com.bottlebattle.bottlebattle.Activities.GetCityActivity;

public class CheckIfGoogleUserExistsCallbackHandler {
    private final Context context;
    private final String username;
    private final String googleIdToken;
    private final String profilePic;
    private final String fireBaseToken;
    private final String baseServerDomain;

    public CheckIfGoogleUserExistsCallbackHandler(Context context, String username, String googleIdToken,
                                                  String profilePic, String fireBaseToken, String baseServerDomain) {
        this.context = context;
        this.username = username;
        this.googleIdToken = googleIdToken;
        this.profilePic = profilePic;
        this.fireBaseToken = fireBaseToken;
        this.baseServerDomain = baseServerDomain;
    }

    public void launchGetCityActivity() {
        //if here, username "is available" - means it isn't yet registered, hence city is unknown
        Intent i = new Intent(context, GetCityActivity.class);
        i.putExtra("username", username);
        i.putExtra("token", googleIdToken);
        i.putExtra("profilePic", profilePic);
        i.putExtra("fireBaseToken", fireBaseToken);
        i.putExtra("baseServerDomain", baseServerDomain);
        context.startActivity(i);
    }

    public void initiateGoogleSignIn() {
        GoogleSignInRequestDTO googleSignInRequest = new GoogleSignInRequestDTO(googleIdToken, profilePic, fireBaseToken, null);
        //city isn't really null, but server doesn't check it anyway (as user already registered before)
        GoogleSignInCallbackHandler callbackHandler =
                new GoogleSignInCallbackHandler(context, username, baseServerDomain);
        new TokenAPI(baseServerDomain).googleSignIn(googleSignInRequest, callbackHandler);
    }
}
