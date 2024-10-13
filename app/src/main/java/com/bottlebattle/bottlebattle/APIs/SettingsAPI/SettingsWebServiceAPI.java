package com.bottlebattle.bottlebattle.APIs.SettingsAPI;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.GetUserByUsernameResDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SettingsWebServiceAPI {
    @GET("/api/Users/{username}")
    Call<GetUserByUsernameResDTO> getUserByUsername(@Header("Authorization") String token, @Path("username") String username);

    @PUT("/api/Users/City/{username}")
    Call<ServerResponseDTO> updateUserCity(@Header("Authorization") String token, @Path("username") String username, @Body CityDTO city);

    @PUT("/api/Users/ProfilePic/{username}")
    Call<ServerResponseDTO> updateUserProfilePic(@Header("Authorization") String token, @Path("username") String username, @Body ProfilePicDTO profilePic);

    @DELETE("/api/Users/DeleteUser/{username}")
    Call<ServerResponseDTO> deleteUser(@Header("Authorization") String token, @Path("username") String username);
}
