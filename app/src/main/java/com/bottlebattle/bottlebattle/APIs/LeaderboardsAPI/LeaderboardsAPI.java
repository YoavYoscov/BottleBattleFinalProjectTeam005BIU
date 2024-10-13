package com.bottlebattle.bottlebattle.APIs.LeaderboardsAPI;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaderboardsAPI {
    private final LeaderboardsWebServiceAPI leaderboardsWebServiceAPI;
    private final String token;
    public LeaderboardsAPI(String baseServerDomain, String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        leaderboardsWebServiceAPI = retrofit.create(LeaderboardsWebServiceAPI.class);
        this.token = token;
    }
    public void getUserCityLeaderboard(String username, GetLeaderboardCallbackHandler callbackHandler, boolean displayInTable, boolean updateTop3) {
        Call<LeaderboardsServerResDTO> call = leaderboardsWebServiceAPI.getUserCityLeaderboard(token, username);
        call.enqueue(new Callback<LeaderboardsServerResDTO>() {
            @Override
            public void onResponse(@NonNull Call<LeaderboardsServerResDTO> call, @NonNull Response<LeaderboardsServerResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<UserDetailsDTO> usersListRes = response.body().getData();
                    callbackHandler.updateLeaderboards(usersListRes, displayInTable, updateTop3);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LeaderboardsServerResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void getUserCountryLeaderboard(String username, GetLeaderboardCallbackHandler callbackHandler, boolean displayInTable, boolean updateTop3) {
        Call<LeaderboardsServerResDTO> call = leaderboardsWebServiceAPI.getUserCountryLeaderboard(token, username);
        call.enqueue(new Callback<LeaderboardsServerResDTO>() {
            @Override
            public void onResponse(@NonNull Call<LeaderboardsServerResDTO> call, @NonNull Response<LeaderboardsServerResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<UserDetailsDTO> usersListRes = response.body().getData();
                    callbackHandler.updateLeaderboards(usersListRes, displayInTable, updateTop3);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LeaderboardsServerResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void getUserFriendsLeaderboard(String username, GetLeaderboardCallbackHandler callbackHandler, boolean displayInTable, boolean updateTop3) {
        Call<LeaderboardsServerResDTO> call = leaderboardsWebServiceAPI.getUserFriendsLeaderboard(token, username);
        call.enqueue(new Callback<LeaderboardsServerResDTO>() {
            @Override
            public void onResponse(@NonNull Call<LeaderboardsServerResDTO> call, @NonNull Response<LeaderboardsServerResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<UserDetailsDTO> usersListRes = response.body().getData();
                    callbackHandler.updateLeaderboards(usersListRes, displayInTable, updateTop3);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LeaderboardsServerResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
}
