package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

public class AddFriendByUsernameReqDTO {
    private String username;
    private String friendUsername;

    public AddFriendByUsernameReqDTO(String username, String friendUsername) {
        this.username = username;
        this.friendUsername = friendUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }
}
