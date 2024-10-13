package com.bottlebattle.bottlebattle.APIs.citiesAPI;

public class CitiesResponseDTO {
    private String help;
    private boolean success;
    private CitiesResponseResult result;

    public CitiesResponseDTO(String help, boolean success, CitiesResponseResult result) {
        this.help = help;
        this.success = success;
        this.result = result;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CitiesResponseResult getResult() {
        return result;
    }

    public void setResult(CitiesResponseResult result) {
        this.result = result;
    }
}
