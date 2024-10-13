package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFriendsAPI {
    private final AddFriendsWebServiceAPI addFriendsWebServiceAPI;
    private final String token;

    public AddFriendsAPI(String baseServerDomain, String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        addFriendsWebServiceAPI = retrofit.create(AddFriendsWebServiceAPI.class);
        this.token = token;
    }

    public void updateUsersList(String username, GetUsersListCallbackHandler callbackHandler) {
        Call<GetUsersListResDTO> call = addFriendsWebServiceAPI.updateUsersList(token, username);
        call.enqueue(new Callback<GetUsersListResDTO>() {
            @Override
            public void onResponse(@NonNull Call<GetUsersListResDTO> call, @NonNull Response<GetUsersListResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<UserDetailsDTO> usersListRes = response.body().getData();
                    callbackHandler.onSuccess(usersListRes, username);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUsersListResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void addFriendByUsername(AddFriendByUsernameReqDTO addFriendByUsernameReq,
                                    AddFriendByUsernameCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = addFriendsWebServiceAPI.addFriendByUsername(token, addFriendByUsernameReq);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                String friendUsername = addFriendByUsernameReq.getFriendUsername();
                if (response.isSuccessful()) callbackHandler.onSuccess(friendUsername);
                else callbackHandler.onError(response.code(), friendUsername);
            }
            @Override
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
}
