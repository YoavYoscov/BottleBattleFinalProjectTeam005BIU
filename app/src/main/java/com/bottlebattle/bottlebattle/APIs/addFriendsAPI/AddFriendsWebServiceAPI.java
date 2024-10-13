package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddFriendsWebServiceAPI {
    @GET("/api/Leaderboard/yourCountry/{username}") //use country leaderboard res to get users list
    Call<GetUsersListResDTO> updateUsersList(@Header("Authorization") String token, @Path("username") String username);

    @POST("/api/Users/AddFriend")
    Call<ServerResponseDTO> addFriendByUsername(@Header("Authorization") String token, @Body AddFriendByUsernameReqDTO addFriendByUsernameReq);
}
