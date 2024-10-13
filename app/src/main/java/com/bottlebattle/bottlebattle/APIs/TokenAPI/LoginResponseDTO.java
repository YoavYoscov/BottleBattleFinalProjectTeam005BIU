package com.bottlebattle.bottlebattle.APIs.TokenAPI;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;

public class LoginResponseDTO extends ServerResponseDTO {
    private String data;

    public LoginResponseDTO(boolean success, String message, int status, String data) {
        super(success, message, status);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
