package com.bottlebattle.bottlebattle.APIs.citiesAPI;

public class CitiesResponseResultLinks {
    private String start;
    private String next;

    public CitiesResponseResultLinks(String start, String next) {
        this.start = start;
        this.next = next;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
