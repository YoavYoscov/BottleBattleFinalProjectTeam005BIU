package com.bottlebattle.bottlebattle.APIs.LeaderboardsAPI;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bottlebattle.bottlebattle.Activities.UserRankingsActivity.TableAdapter;
import com.bottlebattle.bottlebattle.Activities.UserRankingsActivity.TableScrollHandler;
import com.bottlebattle.bottlebattle.Activities.UserRankingsActivity.Top3Updater;
import com.bottlebattle.bottlebattle.R;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;
import java.util.ArrayList;
import java.util.List;

public class GetLeaderboardCallbackHandler {
    private final Context context;
    private final String username;
    private final RecyclerView tableContentRV;
    private final TableAdapter tableAdapter;
    private final ArrayList<UserDetailsDTO> usersList;
    private final Top3Updater top3Updater;
    private final ObjectAnimator refreshBtnAnimator;
    public GetLeaderboardCallbackHandler(Context context, String username, RecyclerView tableContentRV,
                                         TableAdapter tableAdapter, ArrayList<UserDetailsDTO> usersList,
                                         TextView firstPlaceVal, TextView secondPlaceVal, TextView thirdPlaceVal,
                                         ObjectAnimator refreshBtnAnimator) {
        this.context = context;
        this.username = username;
        this.tableContentRV = tableContentRV;
        this.tableAdapter = tableAdapter;
        this.usersList = usersList;
        top3Updater = new Top3Updater(firstPlaceVal, secondPlaceVal, thirdPlaceVal);
        this.refreshBtnAnimator = refreshBtnAnimator;
    }

    public void updateLeaderboards(List<UserDetailsDTO> usersListRes, boolean displayInTable,
                                   boolean updateTop3) {
        usersList.clear();
        usersList.addAll(usersListRes);
        if (displayInTable) {
            tableAdapter.updateTable(usersList);
            TableScrollHandler tableScrollHandler = new TableScrollHandler(username, tableContentRV);
            tableScrollHandler.scrollToUserPosition(usersList);
        }
        if (updateTop3) top3Updater.updateTop3(usersList);
        if (displayInTable) removeProgressBar();
        // removeProgressBar() is only necessary in first call, but insensible and thus unharmful on other calls.
        // On screen start, updateLeaderboards runs simultaneously 3 times (for country, city and friends).
        // We want to stop progress bar when country table is loaded and displayed, so displayInTable is used to distinguish between the 3 runs
        if (displayInTable) stopRefreshBtnRotation(); //the same holds when refreshBtn is clicked (we want to stop animation only when displayed table is updated)
    }

    private void removeProgressBar() {
        //remove progress bar:
        ProgressBar progressBar = ((Activity) context).findViewById(R.id.leaderboardsProgressBar);
        progressBar.setVisibility(View.GONE);
        //enable screen touch-ability after loading:
        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void stopRefreshBtnRotation() {
        //stop animation when current rotation completes:
        //time passed so far:
        long currentPlayTime = refreshBtnAnimator.getCurrentPlayTime();
        //time left for current rotation (as duration is the time for a single rotation):
        long remainingTime = refreshBtnAnimator.getDuration() - (currentPlayTime % refreshBtnAnimator.getDuration());

        // delay the end of the animation for the remainingTime (and then stop)
        (new Handler()).postDelayed(() -> {
            refreshBtnAnimator.end(); // Stop the animation
            //enable screen touch-ability after data is loaded:
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }, remainingTime);
    }
}
