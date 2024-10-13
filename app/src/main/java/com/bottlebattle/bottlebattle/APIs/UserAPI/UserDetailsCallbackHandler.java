package com.bottlebattle.bottlebattle.APIs.UserAPI;

import android.content.Context;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsCallbackHandler { //display user details on home screen
    private final Context context;
    private final TextView moneyAmountTV;
    private final CircleImageView profilePic;
    private final TextView tableLevelValue;
    private final TextView tablePointsValue;

    public UserDetailsCallbackHandler(Context context, TextView moneyAmountTV, CircleImageView profilePic,
                                      TextView tableLevelValue, TextView tablePointsValue) {
        this.context = context;
        this.moneyAmountTV = moneyAmountTV;
        this.profilePic = profilePic;
        this.tableLevelValue = tableLevelValue;
        this.tablePointsValue = tablePointsValue;
    }

    public void updateUserDetails(UserDetailsDTO userDetails) {
        updateMoneyAmount(userDetails.getMoneySaved(), moneyAmountTV);
        updateProfilePic(context, userDetails.getProfilePic(), profilePic);
        updateLevel_Points(userDetails.getUserLevel(), userDetails.getUserPoints(), tableLevelValue, tablePointsValue);
    }

    private void updateMoneyAmount(double moneyRes, TextView moneyAmountTV) {
        String moneyAmountSTR = ((int) moneyRes) + " ILS";
        moneyAmountTV.setText(moneyAmountSTR);
    }
    private void updateProfilePic(Context context, String profilePicRes, CircleImageView imageView) {
        ProfilePicUpdater profilePicUpdater = new ProfilePicUpdater();
        profilePicUpdater.updateProfilePic(context, profilePicRes, imageView);
    }
    private void updateLevel_Points(int levelRes, double pointsRes, TextView tableLevelValue, TextView tablePointsValue){
        tableLevelValue.setText(getLevelName(levelRes));
        int points = (int) pointsRes; //convert points received from server to int before display
        tablePointsValue.setText(Integer.toString(points));
    }
    private String getLevelName(int levelNum) {
        String levelName;
        switch (levelNum) {
            case 0:
            case 1:
                levelName = "Novice Recycler";
                break;
            case 2:
                levelName = "Intermediate Recycler";
                break;
            case 3:
                levelName = "Expert Recycler";
                break;
            case 4:
                levelName = "Recycling Wizard";
                break;
            case 5:
                levelName = "Recycling Master";
                break;
            default:
                // Shouldn't happen, but just in case, we'll return "Beginner" as the user level:
                levelName = "Beginner";
                break;
        }
        return levelName;
    }
}
