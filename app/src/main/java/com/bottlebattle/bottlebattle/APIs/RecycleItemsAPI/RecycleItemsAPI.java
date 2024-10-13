package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecycleItemsAPI {
    private final RecycleItemWebServiceAPI recycleItemWebServiceAPI;
    private final String baseServerDomain;
    private final String token;
    public RecycleItemsAPI(String baseServerDomain, String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        recycleItemWebServiceAPI = retrofit.create(RecycleItemWebServiceAPI.class);
        this.baseServerDomain = baseServerDomain;
        this.token = token;
    }

    public void getAllRecycleItems(ProductsListCallbackHandler callbackHandler) {
        Call<AllRecycleItemsResDTO> call = recycleItemWebServiceAPI.getAllRecycleItems(token);
        call.enqueue(new Callback<AllRecycleItemsResDTO>() {
            @Override
            public void onResponse(@NonNull Call<AllRecycleItemsResDTO> call, @NonNull Response<AllRecycleItemsResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<RecycleItemDTO> allItems = response.body().getData();
                    callbackHandler.updateListsAndProductsDropDownMenu(allItems);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllRecycleItemsResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
    public void addRecycleTransaction(AddRecycleTransactionCallbackHandler callbackHandler, RecycleTransactionReqDTO recycleTransactionReq) {
        Call<ServerResponseDTO> call = recycleItemWebServiceAPI.addRecycleTransaction(token, recycleTransactionReq);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess(baseServerDomain, token);
                } else {
                    callbackHandler.onError();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
    public void getNotesAndSuggestions(String recycleItemId, NotesAndSuggestionsCallbackHandler callbackHandler) {
        Call<NotesAndSuggestionsResDTO> call = recycleItemWebServiceAPI.getNotesAndSuggestions(token, recycleItemId);
        call.enqueue(new Callback<NotesAndSuggestionsResDTO>() {
            @Override
            public void onResponse(@NonNull Call<NotesAndSuggestionsResDTO> call, @NonNull Response<NotesAndSuggestionsResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String notesAndSuggestions = response.body().getData();
                    callbackHandler.onSuccess(notesAndSuggestions);
                } else {
                    callbackHandler.onError();
                }
            }
            @Override
            public void onFailure(@NonNull Call<NotesAndSuggestionsResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
/*
    public void getBetterAlternativeIfSuchExists(String token, String recycleItemId, TextView alternativeTV) {
        Call<AlternativeResDTO> call = recycleItemWebServiceAPI.getBetterAlternativeIfSuchExists(token, recycleItemId);
        call.enqueue(new Callback<AlternativeResDTO>() {
            @Override
            public void onResponse(Call<AlternativeResDTO> call, Response<AlternativeResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String res = response.body().getData();
                    Log.e("bottleres", res);
                    if (res != null && !res.equals("No better alternative found")) { //if suggestedItem != null, there is a suggested product (o.w. no suggested product has been found)
                        String suggestion = "We found a more eco-friendly alternative:\n" + res + "\nWe recommend using it instead.";
                        alternativeTV.setText(suggestion);
                        alternativeTV.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AlternativeResDTO> call, Throwable t) {
                Log.e("errr", t.getMessage());
            }
        });
    }
*/
}
