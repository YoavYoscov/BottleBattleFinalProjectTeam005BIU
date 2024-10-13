package com.bottlebattle.bottlebattle.Activities.HomeScreenActivity.Menu;

import android.content.Context;
import android.content.Intent;

import com.bottlebattle.bottlebattle.Activities.AddFriendsActivity;
import com.bottlebattle.bottlebattle.Activities.LevelProgressActivity;
import com.bottlebattle.bottlebattle.Activities.SettingsActivity;
import com.bottlebattle.bottlebattle.Activities.StatsActivity;
import com.bottlebattle.bottlebattle.Activities.RecycleItemActivities.RecycleItemActivity;
import com.bottlebattle.bottlebattle.Activities.UserRankingsActivity.UserRankingsActivity;

public class MenuClickHandler {
    private final Context context;
    private final String username; //passed to other screens for api requests
    private final String token;  //passed to other screens for api requests
    private final String baseServerDomain; //passed to other screens for api requests

    public MenuClickHandler(Context context, String username, String token, String baseServerDomain) {
        this.context = context;
        this.username = username;
        this.token = token;
        this.baseServerDomain = baseServerDomain;
    }

    public void handleClick(int position) {
        switch(position) {
            case 0:
                Intent i0 = new Intent(context, RecycleItemActivity.class);
                attachUsernameTokenAndServerDomain(i0);
                context.startActivity(i0);
                break;
            case 1:
                Intent i1 = new Intent(context, UserRankingsActivity.class);
                attachUsernameTokenAndServerDomain(i1);
                context.startActivity(i1);
                break;
            case 2:
                Intent i2 = new Intent(context, LevelProgressActivity.class);
                attachUsernameTokenAndServerDomain(i2);
                context.startActivity(i2);
                break;
            case 3:
                Intent i3 = new Intent(context, SettingsActivity.class);
                attachUsernameTokenAndServerDomain(i3);
                context.startActivity(i3);
                break;
            case 4:
                Intent i4 = new Intent(context, AddFriendsActivity.class);
                attachUsernameTokenAndServerDomain(i4);
                context.startActivity(i4);
                break;
            case 5:
                Intent i5 = new Intent(context, StatsActivity.class);
                attachUsernameTokenAndServerDomain(i5);
                context.startActivity(i5);
                break;
            case 6:
                // We want to redirect the user to the privacy policy page - a web page:
                Intent i6 = new Intent(Intent.ACTION_VIEW);
                i6.setData(android.net.Uri.parse("https://sites.google.com/view/bottlebattle/privacy-policy"));
                context.startActivity(i6);
        }
    }

    private void attachUsernameTokenAndServerDomain(Intent i) {
        i.putExtra("username", username);
        i.putExtra("token", token);
        i.putExtra("baseServerDomain", baseServerDomain);
    }
}
