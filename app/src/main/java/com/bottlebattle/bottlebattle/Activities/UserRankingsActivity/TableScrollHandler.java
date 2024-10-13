package com.bottlebattle.bottlebattle.Activities.UserRankingsActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.ArrayList;

public class TableScrollHandler {

    private final String username;
    private final RecyclerView tableContentRV;

    public TableScrollHandler(String username, RecyclerView tableContentRV) {
        this.username = username;
        this.tableContentRV = tableContentRV;
    }

    public void scrollToUserPosition(ArrayList<UserDetailsDTO> usersList) {
        tableContentRV.scrollToPosition(getUserPosition(usersList));
    }

    private int getUserPosition(ArrayList<UserDetailsDTO> usersList) {
        int position = 0, len = usersList.size();
        for (int i = 0; i < len; i++) {
            if (usersList.get(i).getUsername().trim().equals(username.trim())) {
                position = i;
                break;
            }
        }
        return position;
    }
}
