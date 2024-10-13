package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;

public class NotesAndSuggestionsResDTO extends ServerResponseDTO {
    private String data;

    public NotesAndSuggestionsResDTO(boolean success, String message, int status) {
        super(success, message, status);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
