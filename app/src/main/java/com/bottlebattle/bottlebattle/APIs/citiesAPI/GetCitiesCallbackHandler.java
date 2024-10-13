package com.bottlebattle.bottlebattle.APIs.citiesAPI;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class GetCitiesCallbackHandler {
    private final Context context;
    private final AutoCompleteTextView city;
    private final ArrayList<String> cities;

    public GetCitiesCallbackHandler(Context context, AutoCompleteTextView city, ArrayList<String> cities) {
        this.context = context;
        this.city = city;
        this.cities = cities;
    }

    public void updateCitiesDropDownList(CitiesResponseResult citiesResponseResult) {
        ArrayList<CitiesResponseResultRecord> records = citiesResponseResult.getRecords();
        for (CitiesResponseResultRecord record : records) {
            //reformat city name to have only its first letter capitalized, and insert into cities
            String lowerCaseCityName = record.getCity_name_en().toLowerCase();
            if (lowerCaseCityName.length() > 1) { //if cityName isn't " "
                String cityName = lowerCaseCityName.substring(0,1).toUpperCase() + lowerCaseCityName.substring(1,lowerCaseCityName.length()-1);
                cities.add(cityName);
            }
        }
        //set the adapter for the cities drop-down menu:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_item, cities);
        city.setAdapter(adapter);
        city.setThreshold(1); //starts suggesting from 1st character
    }
}
