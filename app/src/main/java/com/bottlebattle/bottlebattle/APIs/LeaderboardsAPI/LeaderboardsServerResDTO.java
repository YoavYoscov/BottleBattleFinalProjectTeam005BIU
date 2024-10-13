package com.bottlebattle.bottlebattle.APIs.LeaderboardsAPI;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.List;

public class LeaderboardsServerResDTO {
    private boolean success;
    private String message;
    private int status;
    private List<UserDetailsDTO> data;

    public LeaderboardsServerResDTO(boolean success, String message, int status, List<UserDetailsDTO> data) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<UserDetailsDTO> getData() {
        return data;
    }

    public void setData(List<UserDetailsDTO> data) {
        this.data = data;
    }

}
