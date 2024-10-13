package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecycleItemWebServiceAPI {

    /* GET request to get user details */
    @GET("/api/RecycleItems/")
    Call<AllRecycleItemsResDTO> getAllRecycleItems(@Header("Authorization") String token);

    @POST("/api/RecycleTransactions/")
    Call<ServerResponseDTO> addRecycleTransaction(@Header("Authorization") String token, @Body RecycleTransactionReqDTO recycleTransactionReq);

    @GET("/api/RecycleItems/GetNotesAndSuggestions/{objectId}")
    Call<NotesAndSuggestionsResDTO> getNotesAndSuggestions(@Header("Authorization") String token, @Path("objectId") String recycleItemId);

/*
    @GET("/api/RecycleItems/GetBetterAlternativeIfSuchExists/{id}")
    Call<AlternativeResDTO> getBetterAlternativeIfSuchExists(@Header("Authorization") String token, @Path("id") String recycleItemId);
*/

}
