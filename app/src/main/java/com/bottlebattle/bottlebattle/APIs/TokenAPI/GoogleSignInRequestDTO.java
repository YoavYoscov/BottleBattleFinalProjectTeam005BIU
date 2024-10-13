package com.bottlebattle.bottlebattle.APIs.TokenAPI;

public class GoogleSignInRequestDTO  {
    private String token;
    private String profilePic;
    private String fireBaseToken;
    private String city;

    public GoogleSignInRequestDTO(String token, String profilePic, String fireBaseToken, String city) {
        this.token = token;
        this.profilePic = profilePic;
        this.fireBaseToken = fireBaseToken;
        this.city = city;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
