package com.bottlebattle.bottlebattle.APIs.LeaderboardsAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LeaderboardsWebServiceAPI {

    @GET("/api/Leaderboard/yourCity/{username}")
    Call<LeaderboardsServerResDTO> getUserCityLeaderboard(@Header("Authorization") String token, @Path("username") String username);

    @GET("/api/Leaderboard/yourCountry/{username}")
    Call<LeaderboardsServerResDTO> getUserCountryLeaderboard(@Header("Authorization") String token, @Path("username") String username);

    @GET("/api/Leaderboard/yourFriends/{username}")
    Call<LeaderboardsServerResDTO> getUserFriendsLeaderboard(@Header("Authorization") String token, @Path("username") String username);

}
