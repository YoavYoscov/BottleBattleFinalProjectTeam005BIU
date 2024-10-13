package com.bottlebattle.bottlebattle.APIs.TokenAPI;
import android.util.Log;
import androidx.annotation.NonNull;
import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserWebServiceAPI;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAPI {
    private final TokenWebServiceAPI tokenWebServiceAPI;
    private final UserWebServiceAPI userWebServiceAPI;
    private final String baseServerDomain;

    public TokenAPI(String baseServerDomain) {
        //the full url is determined by the concatenation of the base address and the url in the UserWebServiceAPI interface
        //gson is used to parse objects into jsons and vice-versa.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        tokenWebServiceAPI = retrofit.create(TokenWebServiceAPI.class);
        userWebServiceAPI = retrofit.create(UserWebServiceAPI.class);
        this.baseServerDomain = baseServerDomain;
    }

    /* request format: POST DOMAIN/api/Tokens
     * performs default login (and retrieves jwt token) */
    public void defaultLogin(DefaultLoginRequestDTO loginRequest, DefaultLoginCallbackHandler callbackHandler) {
        Call<LoginResponseDTO> call = tokenWebServiceAPI.defaultLogin(loginRequest);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseDTO> call, @NonNull Response<LoginResponseDTO> response) {
                if (response.isSuccessful()) {
                    //if here, user provided correct username & password:
                    assert response.body() != null;
                    String token = response.body().getData(); //jwt token
                    String username = loginRequest.getUsername();
                    callbackHandler.onSuccess(token, username, baseServerDomain);
                } else {
                    //if here, username or password are incorrect:
                    //as retrofit doesn't serialize the response body when status code isn't 20x (success), we use org.json.JSONObject instead
                    try {
                        JSONObject errorJson = new JSONObject(response.errorBody().string()); //serialize response body
                        String errorMessage = errorJson.getString("message");
                        callbackHandler.onError(errorMessage);
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    /* request format: POST DOMAIN/api/Tokens/google
     * performs sign-in via google (and retrieves jwt token) */
    public void checkIfGoogleUserExistsAndSignIn(String username, CheckIfGoogleUserExistsCallbackHandler callbackHandler) {
        //first we check if username already exists. If not, this is the first time this google user
        // logs in, so we need to ask for its city (so we start the GetCityActivity).
        // Otherwise, If user already exists, we sign him in using the googleSignIn method below

        // check if this google account already has a user in our server (via checkAvailableUsername):
        Call<ServerResponseDTO> call = userWebServiceAPI.checkAvailableUsername(username);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    //if here, username "is available" - means it isn't yet registered, hence city is unknown
                    callbackHandler.launchGetCityActivity();
                } else {
                    //if here, username "is already taken" - means it is already registered, hence city is known
                    callbackHandler.initiateGoogleSignIn();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void googleSignIn(GoogleSignInRequestDTO googleSignInRequest, GoogleSignInCallbackHandler callbackHandler) {
        // sign-in for a registered google user
        Call<LoginResponseDTO> call = tokenWebServiceAPI.googleSignIn(googleSignInRequest);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseDTO> call, @NonNull Response<LoginResponseDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String jwtToken = response.body().getData();
                    callbackHandler.signUserIn(jwtToken);
                } else {
                    Log.e("err", "signinwithgoogleerror");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

}
