package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;
import com.bottlebattle.bottlebattle.R;

import java.util.ArrayList;
import java.util.List;

public class GetUsersListCallbackHandler {
    private final Context context;
    private final AutoCompleteTextView addFriendsACTV;
    private final ArrayList<UserDetailsDTO> usersList;
    private final ArrayList<String> allUsernames;

    public GetUsersListCallbackHandler(Context context, AutoCompleteTextView addFriendsACTV, ArrayList<UserDetailsDTO> usersList,
                                       ArrayList<String> allUsernames) {
        this.context = context;
        this.addFriendsACTV = addFriendsACTV;
        this.usersList = usersList;
        this.allUsernames = allUsernames;
    }

    public void onSuccess(List<UserDetailsDTO> usersListRes, String loggedInUsername) {
        for (UserDetailsDTO user : usersListRes) {
            if (user.getUsername() != null && !(user.getUsername().equals(loggedInUsername))) {
                //update users list, without logged in user
                usersList.add(user); //usersList = list of UserDetailsDTO objects
                allUsernames.add(user.getUsername()); //all usernames = list of strings
            }
        }
        //set the adapter for the users drop-down menu:
        UsersListAdapter adapter = new UsersListAdapter(context, R.layout.user_element_in_list,usersList);
        addFriendsACTV.setAdapter(adapter);
        addFriendsACTV.setThreshold(1); //starts suggesting from 1st character
        //remove progress bar after retrieving users list:
        ProgressBar progressBar = ((Activity) context).findViewById(R.id.addFriendsProgressBar);
        progressBar.setVisibility(View.GONE);
        //at last, enable screen touch-ability:
        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
