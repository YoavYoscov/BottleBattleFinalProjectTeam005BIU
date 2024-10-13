package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import java.util.List;

public class AllRecycleItemsResDTO {
    private boolean success;
    private String message;
    private int status;
    private List<RecycleItemDTO> data;

    public AllRecycleItemsResDTO(boolean success, String message, int status, List<RecycleItemDTO> data) {
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

    public List<RecycleItemDTO> getData() {
        return data;
    }

    public void setData(List<RecycleItemDTO> data) {
        this.data = data;
    }
}
