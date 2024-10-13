package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import java.util.List;

public class RecycleItemDTO {
    private String _id;
    private List<String> barcode;
    private String brand;
    private String manufacturer;
    private String productName;
    private String suitableRecycleBin;
    private String notesAndSuggestions;
    private int isHadap;
    private int __v;

    public RecycleItemDTO(String _id, List<String> barcode, String brand, String manufacturer, String productName,
                          String suitableRecycleBin, String notesAndSuggestions, int isHadap, int __v) {
        this._id = _id;
        this.barcode = barcode;
        this.brand = brand;
        this.manufacturer = manufacturer;
        this.productName = productName;
        this.suitableRecycleBin = suitableRecycleBin;
        this.notesAndSuggestions = notesAndSuggestions;
        this.isHadap = isHadap;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getBarcode() {
        return barcode;
    }

    public void setBarcode(List<String> barcode) {
        this.barcode = barcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSuitableRecycleBin() {
        return suitableRecycleBin;
    }

    public void setSuitableRecycleBin(String suitableRecycleBin) {
        this.suitableRecycleBin = suitableRecycleBin;
    }

    public String getNotesAndSuggestions() {
        return notesAndSuggestions;
    }

    public void setNotesAndSuggestions(String notesAndSuggestions) {
        this.notesAndSuggestions = notesAndSuggestions;
    }

    public int getIsHadap() {
        return isHadap;
    }

    public void setIsHadap(int isHadap) {
        this.isHadap = isHadap;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
