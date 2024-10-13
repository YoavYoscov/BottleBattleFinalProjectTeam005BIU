package com.bottlebattle.bottlebattle.APIs.UserAPI;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserWebServiceAPI {
    //this interface is required by the retrofit library.
    // It defines the calls (including the format and urls) of the api requests.

    /* GET request to check if the given username is available */
    @GET("/api/Users/CheckAvailableUsername/{username}")
    Call<ServerResponseDTO> checkAvailableUsername(@Path("username") String username);

    /* POST request to register a user */
    @POST("/api/Users")
    Call<ServerResponseDTO> createUser(@Body CreateUserReqDTO user);

    /* GET request to get user details */
    @GET("/api/Users/{username}")
    Call<GetUserByUsernameResDTO> getUserByUsername(@Header("Authorization") String token, @Path("username") String username);

    /* GET request to recover user's password (retrieve token via email) */
    @GET("/api/Users/ForgotPassword/{username}")
    Call<ServerResponseDTO> forgotPassword(@Path("username") String username);

    /* POST request to reset user's password */
    @POST("/api/Users/ResetPassword")
    Call<ServerResponseDTO> resetPassword(@Body ResetPasswordReqDTO resetPasswordReq);
}
