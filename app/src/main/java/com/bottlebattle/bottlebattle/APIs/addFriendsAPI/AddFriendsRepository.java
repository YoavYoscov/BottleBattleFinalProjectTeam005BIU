package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.ArrayList;

public class AddFriendsRepository {
    private final AddFriendsAPI addFriendsAPI;
    private final String username;
    private final ArrayList<UserDetailsDTO> usersList = new ArrayList<>();
    private final ArrayList<String> allUsernames = new ArrayList<>();

    public AddFriendsRepository(String token, String baseServerDomain, String username) {
        this.addFriendsAPI = new AddFriendsAPI(baseServerDomain, token);
        this.username = username;
    }
    public ArrayList<String> getAllUsernames() {
        return allUsernames;
    }
    public void updateFriendsList(Context context, AutoCompleteTextView addFriendsACTV) {
        GetUsersListCallbackHandler callbackHandler = new GetUsersListCallbackHandler(context, addFriendsACTV, usersList, allUsernames);
        this.addFriendsAPI.updateUsersList(username, callbackHandler);
    }
    public void addFriendByUsername(Context context, String friendUsername, TextView addFriendsFeedback, AutoCompleteTextView addFriendsACTV) {
        AddFriendByUsernameReqDTO addFriendByUsernameReq = new AddFriendByUsernameReqDTO(username, friendUsername);
        AddFriendByUsernameCallbackHandler callbackHandler =
                new AddFriendByUsernameCallbackHandler(context, addFriendsFeedback, addFriendsACTV);
        this.addFriendsAPI.addFriendByUsername(addFriendByUsernameReq, callbackHandler);
    }
}
