package com.bottlebattle.bottlebattle.APIs.SettingsAPI;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.GetUserByUsernameResDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsAPI {
    private final SettingsWebServiceAPI settingsWebServiceAPI;
    private final String token;
    public SettingsAPI(String baseServerDomain, String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        settingsWebServiceAPI = retrofit.create(SettingsWebServiceAPI.class);
        this.token = token;
    }

    public void getUserByUsername(String username, UserDetailsSettingsCallbackHandler callbackHandler) {
        Call<GetUserByUsernameResDTO> call = settingsWebServiceAPI.getUserByUsername(token, username);
        call.enqueue(new Callback<GetUserByUsernameResDTO>() {
            @Override
            public void onResponse(@NonNull Call<GetUserByUsernameResDTO> call, @NonNull Response<GetUserByUsernameResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    UserDetailsDTO userDetails = response.body().getData();
                    callbackHandler.updateUserDetails(userDetails);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUserByUsernameResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void updateUserCity(String username, String city, UpdateCityCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = settingsWebServiceAPI.updateUserCity(token, username, new CityDTO(city));
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess();
                } else {
                    callbackHandler.onErr();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void updateUserProfilePic(String username, String profilePic, UpdateProfilePicCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = settingsWebServiceAPI.updateUserProfilePic(token, username, new ProfilePicDTO(profilePic));
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess();
                } else {
                    callbackHandler.onErr();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void deleteUser(String username, DeleteUserCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = settingsWebServiceAPI.deleteUser(token, username);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess();
                } else {
                    callbackHandler.onErr();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
}
