package com.bottlebattle.bottlebattle.APIs.TokenAPI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bottlebattle.bottlebattle.Activities.HomeScreenActivity.HomeScreenActivity;
import com.bottlebattle.bottlebattle.Activities.GetCityActivity;

public class GoogleSignInCallbackHandler {
    private final Context context;
    private final String username;
    private final String baseServerDomain;

    public GoogleSignInCallbackHandler(Context context, String username, String baseServerDomain) {
        this.context = context;
        this.username = username;
        this.baseServerDomain = baseServerDomain;
    }

    public void signUserIn(String jwtToken) {
        Intent i = new Intent(context, HomeScreenActivity.class);
        i.putExtra("token", jwtToken); //jwt token
        i.putExtra("username", username);
        i.putExtra("baseServerDomain", baseServerDomain);
        context.startActivity(i); //display homescreen
        if (context instanceof GetCityActivity) {
            ((Activity) context).finish(); //remove getCityActivity from activities stack
        }
    }
}
