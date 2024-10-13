package com.bottlebattle.bottlebattle.Activities.UserRankingsActivity;

import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.ArrayList;

public class Top3Updater {

    private final TextView firstPlaceVal;
    private final TextView secondPlaceVal;
    private final TextView thirdPlaceVal;

    public Top3Updater(TextView firstPlaceVal, TextView secondPlaceVal, TextView thirdPlaceVal) {
        this.firstPlaceVal = firstPlaceVal;
        this.secondPlaceVal = secondPlaceVal;
        this.thirdPlaceVal = thirdPlaceVal;
    }

    public void updateTop3(ArrayList<UserDetailsDTO> usersList) {
        //first, update 1st place, as it is always possible (list must contain logged-in user, so it is of size at least 1):
        firstPlaceVal.setText(usersList.get(0).getUsername());
        // now, for 2nd and 3rd places:
        //clean previous values:
        secondPlaceVal.setText("-");
        thirdPlaceVal.setText("-");
        // check if there are other users to add:
        int len = usersList.size();
        if (len > 1) secondPlaceVal.setText(usersList.get(1).getUsername());
        if (len > 2) thirdPlaceVal.setText(usersList.get(2).getUsername());
    }
}
