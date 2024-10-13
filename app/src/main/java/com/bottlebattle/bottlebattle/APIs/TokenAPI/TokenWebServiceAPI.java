package com.bottlebattle.bottlebattle.APIs.TokenAPI;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TokenWebServiceAPI {
    //this interface is required by the retrofit library.
    // It defines the calls (including the format and urls) of the api requests.

    /* POST request for default login (if successful, retrieves a token) */
    @POST("/api/Tokens")
    Call<LoginResponseDTO> defaultLogin(@Body DefaultLoginRequestDTO loginRequest);

    /* POST request for google sign-in (if successful, retrieves a token) */
    @POST("/api/Tokens/google")
    Call<LoginResponseDTO> googleSignIn(@Body GoogleSignInRequestDTO signInRequest);
}
