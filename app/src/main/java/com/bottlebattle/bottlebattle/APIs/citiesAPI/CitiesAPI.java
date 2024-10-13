package com.bottlebattle.bottlebattle.APIs.citiesAPI;
import androidx.annotation.NonNull;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CitiesAPI {
    private final CitiesWebServiceAPI citiesWebServiceAPI; //the interface required by retrofit in which the types
    // (GET/POST) and the urls of the requests are defined.
    public CitiesAPI() {
        String baseAddress = "https://data.gov.il/api/";
        //the full url is determined by the concatenation of the base address and the url in the CitiesWebServiceAPI interface
        //gson is used to parse objects into jsons and vice-versa.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseAddress).addConverterFactory(GsonConverterFactory.create()).build();
        citiesWebServiceAPI = retrofit.create(CitiesWebServiceAPI.class);
    }

    /* perform GET request. The response is in a format similar to that of the CitiesResponse class
    * The data itself is in its result attribute*/
    public void getCitiesInIsrael(GetCitiesCallbackHandler callbackHandler) {

        Call<CitiesResponseDTO> call = citiesWebServiceAPI.getCitiesInIsrael();
        call.enqueue(new Callback<CitiesResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<CitiesResponseDTO> call, @NonNull Response<CitiesResponseDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    CitiesResponseResult citiesResponseResult = response.body().getResult();
                    callbackHandler.updateCitiesDropDownList(citiesResponseResult);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CitiesResponseDTO> call, @NonNull Throwable t) {
                //must override hence is here
            }
        });
    }
}
