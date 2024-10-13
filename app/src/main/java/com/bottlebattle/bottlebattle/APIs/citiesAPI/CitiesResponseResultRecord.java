package com.bottlebattle.bottlebattle.APIs.citiesAPI;

public class CitiesResponseResultRecord {
    private int id;
    private int city_code;
    private String city_name_he;
    private String city_name_en;
    private int region_code;
    private String region_name;
    private int PIBA_bureau_code;
    private String PIBA_bureau_name;
    private int Regional_Council_code;
    private String Regional_Council_name;

    public CitiesResponseResultRecord(int id, int city_code, String city_name_he, String city_name_en,
                                      int region_code, String region_name, int PIBA_bureau_code,
                                      String PIBA_bureau_name, int regional_Council_code,
                                      String regional_Council_name) {
        this.id = id;
        this.city_code = city_code;
        this.city_name_he = city_name_he;
        this.city_name_en = city_name_en;
        this.region_code = region_code;
        this.region_name = region_name;
        this.PIBA_bureau_code = PIBA_bureau_code;
        this.PIBA_bureau_name = PIBA_bureau_name;
        Regional_Council_code = regional_Council_code;
        Regional_Council_name = regional_Council_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_code() {
        return city_code;
    }

    public void setCity_code(int city_code) {
        this.city_code = city_code;
    }

    public String getCity_name_he() {
        return city_name_he;
    }

    public void setCity_name_he(String city_name_he) {
        this.city_name_he = city_name_he;
    }

    public String getCity_name_en() {
        return city_name_en;
    }

    public void setCity_name_en(String city_name_en) {
        this.city_name_en = city_name_en;
    }

    public int getRegion_code() {
        return region_code;
    }

    public void setRegion_code(int region_code) {
        this.region_code = region_code;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getPIBA_bureau_code() {
        return PIBA_bureau_code;
    }

    public void setPIBA_bureau_code(int PIBA_bureau_code) {
        this.PIBA_bureau_code = PIBA_bureau_code;
    }

    public String getPIBA_bureau_name() {
        return PIBA_bureau_name;
    }

    public void setPIBA_bureau_name(String PIBA_bureau_name) {
        this.PIBA_bureau_name = PIBA_bureau_name;
    }

    public int getRegional_Council_code() {
        return Regional_Council_code;
    }

    public void setRegional_Council_code(int regional_Council_code) {
        Regional_Council_code = regional_Council_code;
    }

    public String getRegional_Council_name() {
        return Regional_Council_name;
    }

    public void setRegional_Council_name(String regional_Council_name) {
        Regional_Council_name = regional_Council_name;
    }
}
