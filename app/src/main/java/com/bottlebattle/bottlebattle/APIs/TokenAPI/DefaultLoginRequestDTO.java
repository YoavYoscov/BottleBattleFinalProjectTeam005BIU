package com.bottlebattle.bottlebattle.APIs.TokenAPI;

public class DefaultLoginRequestDTO {
    private String username;
    private String password;
    private String fireBaseToken;

    public DefaultLoginRequestDTO(String username, String password, String fireBaseToken) {
        this.username = username;
        this.password = password;
        this.fireBaseToken = fireBaseToken;
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

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }
}
