package com.bottlebattle.bottlebattle.APIs.citiesAPI;

public class CitiesResponseResultField {
    private String id;
    private String type;

    public CitiesResponseResultField(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
