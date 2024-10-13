package com.bottlebattle.bottlebattle.APIs.LeaderboardsAPI;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bottlebattle.bottlebattle.Activities.UserRankingsActivity.TableAdapter;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.ArrayList;

public class LeaderboardsRepository {
    private final LeaderboardsAPI leaderboardsAPI;
    private final String username;
    private final ArrayList<UserDetailsDTO> countryUsersList = new ArrayList<>();
    private final ArrayList<UserDetailsDTO> cityUsersList = new ArrayList<>();
    private final ArrayList<UserDetailsDTO> friendsUsersList = new ArrayList<>();

    public LeaderboardsRepository(String baseServerDomain, String token, String username) {
        this.leaderboardsAPI = new LeaderboardsAPI(baseServerDomain, token);
        this.username = username;
    }

    public void getUserCityLeaderboard(Context context, RecyclerView tableContentRV,
                                       TableAdapter tableAdapter, TextView firstPlaceVal,
                                       TextView secondPlaceVal, TextView thirdPlaceVal, boolean displayInTable,
                                       boolean updateTop3, ObjectAnimator refreshBtnAnimator) {
        GetLeaderboardCallbackHandler callbackHandler =
                new GetLeaderboardCallbackHandler(context, username, tableContentRV, tableAdapter,
                        cityUsersList, firstPlaceVal, secondPlaceVal, thirdPlaceVal, refreshBtnAnimator);
        leaderboardsAPI.getUserCityLeaderboard(username, callbackHandler, displayInTable, updateTop3);
    }

    public void getUserCountryLeaderboard(Context context, RecyclerView tableContentRV,
                                          TableAdapter tableAdapter, TextView firstPlaceVal,
                                          TextView secondPlaceVal, TextView thirdPlaceVal, boolean displayInTable,
                                          boolean updateTop3, ObjectAnimator refreshBtnAnimator) {
        GetLeaderboardCallbackHandler callbackHandler =
                new GetLeaderboardCallbackHandler(context, username, tableContentRV, tableAdapter,
                        countryUsersList, firstPlaceVal, secondPlaceVal, thirdPlaceVal, refreshBtnAnimator);
        leaderboardsAPI.getUserCountryLeaderboard(username, callbackHandler, displayInTable, updateTop3);
    }

    public void getUserFriendsLeaderboard(Context context, RecyclerView tableContentRV,
                                          TableAdapter tableAdapter, TextView firstPlaceVal,
                                          TextView secondPlaceVal, TextView thirdPlaceVal, boolean displayInTable,
                                          boolean updateTop3, ObjectAnimator refreshBtnAnimator) {
        GetLeaderboardCallbackHandler callbackHandler =
                new GetLeaderboardCallbackHandler(context, username, tableContentRV, tableAdapter,
                        friendsUsersList, firstPlaceVal, secondPlaceVal, thirdPlaceVal, refreshBtnAnimator);
        leaderboardsAPI.getUserFriendsLeaderboard(username, callbackHandler, displayInTable, updateTop3);
    }

    public ArrayList<UserDetailsDTO> getCountryUsersList() {
        return countryUsersList;
    }

    public ArrayList<UserDetailsDTO> getCityUsersList() {
        return cityUsersList;
    }

    public ArrayList<UserDetailsDTO> getFriendsUsersList() {
        return friendsUsersList;
    }
}
