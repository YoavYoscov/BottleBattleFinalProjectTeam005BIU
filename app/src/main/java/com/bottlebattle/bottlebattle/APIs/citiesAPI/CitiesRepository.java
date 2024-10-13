package com.bottlebattle.bottlebattle.APIs.citiesAPI;
import android.content.Context;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class CitiesRepository {
    private final CitiesAPI citiesAPI;
    private final ArrayList<String> cities;

    public CitiesRepository() {
        citiesAPI = new CitiesAPI();
        cities = new ArrayList<>();
    }
    public void getCitiesInIsrael(Context context, AutoCompleteTextView city) {
        GetCitiesCallbackHandler callbackHandler =
                new GetCitiesCallbackHandler(context, city, cities);
        citiesAPI.getCitiesInIsrael(callbackHandler);
    }
    public ArrayList<String> getCities() {
        return cities;
    }
}
