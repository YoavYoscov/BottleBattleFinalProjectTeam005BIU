package com.bottlebattle.bottlebattle.APIs.citiesAPI;
import java.util.ArrayList;

public class CitiesResponseResult {
    private boolean include_total;
    private int limit;
    private String records_format;
    private String resource_id;
    private transient String total_estimation_threshold;
    private ArrayList<CitiesResponseResultRecord> records;
    private ArrayList<CitiesResponseResultField> fields;
    private CitiesResponseResultLinks _links;
    private int total;
    private boolean total_was_estimated;

    public CitiesResponseResult(boolean include_total, int limit, String records_format,
                                String resource_id, String total_estimation_threshold,
                                ArrayList<CitiesResponseResultRecord> records,
                                ArrayList<CitiesResponseResultField> fields,
                                CitiesResponseResultLinks _links, int total, boolean total_was_estimated) {
        this.include_total = include_total;
        this.limit = limit;
        this.records_format = records_format;
        this.resource_id = resource_id;
        this.total_estimation_threshold = total_estimation_threshold;
        this.records = records;
        this.fields = fields;
        this._links = _links;
        this.total = total;
        this.total_was_estimated = total_was_estimated;
    }

    public boolean isInclude_total() {
        return include_total;
    }

    public void setInclude_total(boolean include_total) {
        this.include_total = include_total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getRecords_format() {
        return records_format;
    }

    public void setRecords_format(String records_format) {
        this.records_format = records_format;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getTotal_estimation_threshold() {
        return total_estimation_threshold;
    }

    public void setTotal_estimation_threshold(String total_estimation_threshold) {
        this.total_estimation_threshold = total_estimation_threshold;
    }

    public ArrayList<CitiesResponseResultRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<CitiesResponseResultRecord> records) {
        this.records = records;
    }

    public ArrayList<CitiesResponseResultField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<CitiesResponseResultField> fields) {
        this.fields = fields;
    }

    public CitiesResponseResultLinks get_links() {
        return _links;
    }

    public void set_links(CitiesResponseResultLinks _links) {
        this._links = _links;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isTotal_was_estimated() {
        return total_was_estimated;
    }

    public void setTotal_was_estimated(boolean total_was_estimated) {
        this.total_was_estimated = total_was_estimated;
    }
}
