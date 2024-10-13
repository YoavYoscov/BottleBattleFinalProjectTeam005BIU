package com.bottlebattle.bottlebattle.APIs.citiesAPI;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CitiesWebServiceAPI {
    //this interface is required by the retrofit library.
    // It defines the calls (including the format and urls) of the api requests.

    /* GET request to the data.gov.il web service to get the list of cities in Israel. further explained
     * in the CitiesAPI class.*/
    @GET("3/action/datastore_search?resource_id=8f714b6f-c35c-4b40-a0e7-547b675eee0e")
    Call<CitiesResponseDTO> getCitiesInIsrael();
}
