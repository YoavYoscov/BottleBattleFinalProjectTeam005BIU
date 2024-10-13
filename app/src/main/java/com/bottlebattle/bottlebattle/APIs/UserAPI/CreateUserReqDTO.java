package com.bottlebattle.bottlebattle.APIs.UserAPI;

public class CreateUserReqDTO {
    private String username;
    private String password;
    private String profilePic;
    private String fireBaseToken;
    private String city;
    private String email;

    public CreateUserReqDTO(String username, String password, String profilePic,
                            String fireBaseToken, String city, String email) {
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
        this.fireBaseToken = fireBaseToken;
        this.city = city;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
