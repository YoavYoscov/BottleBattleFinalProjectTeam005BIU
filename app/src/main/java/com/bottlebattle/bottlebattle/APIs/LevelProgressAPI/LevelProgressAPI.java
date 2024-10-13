package com.bottlebattle.bottlebattle.APIs.LevelProgressAPI;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bottlebattle.bottlebattle.APIs.UserAPI.GetUserByUsernameResDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LevelProgressAPI {
    private final LevelProgressWebServiceAPI levelProgressWebServiceAPI;
    private final String token;
    public LevelProgressAPI(String baseServerDomain, String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        levelProgressWebServiceAPI = retrofit.create(LevelProgressWebServiceAPI.class);
        this.token = token;
    }

    public void getUserByUsername(String username, LevelProgressCallbackHandler callbackHandler) {
        Call<GetUserByUsernameResDTO> call = levelProgressWebServiceAPI.getUserByUsername(token, username);
        call.enqueue(new Callback<GetUserByUsernameResDTO>() {
            @Override
            public void onResponse(@NonNull Call<GetUserByUsernameResDTO> call, @NonNull Response<GetUserByUsernameResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    UserDetailsDTO userDetails = response.body().getData();
                    callbackHandler.onSuccess(userDetails.getUserLevel(), (int) userDetails.getUserPoints());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUserByUsernameResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
}
