package com.bottlebattle.bottlebattle.APIs.StatsAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface StatsWebServiceAPI {
    @GET("/api/Stats/PlasticSaved/{username}")
    Call<StatsResDoubleDTO> getTotalPlasticSavedForUser(@Header("Authorization") String token, @Path("username") String username);

    @GET("/api/Stats/MoneySaved/{username}")
    Call<StatsResDoubleDTO> getTotalMoneySavedForUser(@Header("Authorization") String token, @Path("username") String username);

    @GET("/api/Stats/UserPoints/{username}")
    Call<StatsResDoubleDTO> getUserPoints(@Header("Authorization") String token, @Path("username") String username);

    @GET("/api/Stats/UserLevel/{username}")
    Call<LevelResDTO> getUserLevel(@Header("Authorization") String token, @Path("username") String username);

    @GET("/api/RecycleTransactions/GetDateOfLastTransaction/{username}")
    Call<String> getDateOfLastTransaction(@Header("Authorization") String token, @Path("username") String username);
}
