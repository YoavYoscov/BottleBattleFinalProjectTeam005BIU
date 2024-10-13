package com.bottlebattle.bottlebattle.APIs.LevelProgressAPI;

import com.bottlebattle.bottlebattle.APIs.UserAPI.GetUserByUsernameResDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LevelProgressWebServiceAPI {
    @GET("/api/Users/{username}")
    Call<GetUserByUsernameResDTO> getUserByUsername(@Header("Authorization") String token, @Path("username") String username);
}
