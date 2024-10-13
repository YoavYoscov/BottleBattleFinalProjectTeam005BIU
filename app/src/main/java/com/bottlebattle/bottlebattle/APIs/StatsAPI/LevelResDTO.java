package com.bottlebattle.bottlebattle.APIs.StatsAPI;

public class LevelResDTO {
    private boolean success;
    private String message;
    private int status;
    private String data;

    public LevelResDTO(boolean success, String message, int status, String data) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
