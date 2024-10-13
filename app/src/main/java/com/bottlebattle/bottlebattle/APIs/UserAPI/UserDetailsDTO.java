package com.bottlebattle.bottlebattle.APIs.UserAPI;

import androidx.annotation.NonNull;

import java.util.List;

public class UserDetailsDTO {
    private String username;
    private String profilePic;
    private String fireBaseToken;
    private int userLevel;
    private double userPoints;
    private String city;
    private List<String> friends;
    private double moneySaved;
    private String email;

    public UserDetailsDTO(String username, String profilePic, String fireBaseToken,
                          int userLevel, int userPoints, String city,
                          List<String> friends, int moneySaved, String email) {
        this.username = username;
        this.profilePic = profilePic;
        this.fireBaseToken = fireBaseToken;
        this.userLevel = userLevel;
        this.userPoints = userPoints;
        this.city = city;
        this.friends = friends;
        this.moneySaved = moneySaved;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public double getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public double getMoneySaved() {
        return moneySaved;
    }

    public void setMoneySaved(int moneySaved) {
        this.moneySaved = moneySaved;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        if (username == null) {
            return "";
        }
        return username;
    }
}
