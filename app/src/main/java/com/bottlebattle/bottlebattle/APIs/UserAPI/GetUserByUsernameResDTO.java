package com.bottlebattle.bottlebattle.APIs.UserAPI;

public class GetUserByUsernameResDTO {
    private boolean success;
    private String message;
    private int status;
    private UserDetailsDTO data;

    public GetUserByUsernameResDTO(boolean success, String message, int status, UserDetailsDTO userDetails) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = userDetails;
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

    public UserDetailsDTO getData() {
        return data;
    }

    public void setData(UserDetailsDTO userDetails) {
        this.data = userDetails;
    }
}
