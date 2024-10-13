package com.bottlebattle.bottlebattle.APIs.StatsAPI;

public class StatsResDoubleDTO { //used for plastic, money and points responses
    private boolean success;
    private String message;
    private int status;
    private double data;

    public StatsResDoubleDTO(boolean success, String message, int status, double data) {
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

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
