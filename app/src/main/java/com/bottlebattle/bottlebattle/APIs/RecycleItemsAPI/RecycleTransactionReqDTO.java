package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

public class RecycleTransactionReqDTO {
    private String username;
    private String recycleItemId;
    private int quantity;

    public RecycleTransactionReqDTO(String username, String recycleItemId, int quantity) {
        this.username = username;
        this.recycleItemId = recycleItemId;
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecycleItemId() {
        return recycleItemId;
    }

    public void setRecycleItemId(String recycleItemId) {
        this.recycleItemId = recycleItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
