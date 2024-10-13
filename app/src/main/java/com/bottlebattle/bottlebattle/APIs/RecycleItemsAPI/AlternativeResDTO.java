package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;

public class AlternativeResDTO extends ServerResponseDTO {
    String data;
    public AlternativeResDTO(boolean success, String message, int status, String data) {
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
