package com.bottlebattle.bottlebattle.APIs.StatsAPI;
import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsAPI {
    private final StatsWebServiceAPI statsWebServiceAPI;
    private final String token;
    public StatsAPI(String baseServerDomain, String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        statsWebServiceAPI = retrofit.create(StatsWebServiceAPI.class);
        this.token = token;
    }

    public void getTotalPlasticSavedForUser(String username, PlasticCallbackHandler callbackHandler) {
        Call<StatsResDoubleDTO> call = statsWebServiceAPI.getTotalPlasticSavedForUser(token, username);
        call.enqueue(new Callback<StatsResDoubleDTO>() {
            @Override
            public void onResponse(@NonNull Call<StatsResDoubleDTO> call, @NonNull Response<StatsResDoubleDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    double plasticSaved = response.body().getData();
                    callbackHandler.updatePlasticVal(plasticSaved);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatsResDoubleDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
    public void getTotalMoneySavedForUser(String username, MoneyCallbackHandler callbackHandler) {
        Call<StatsResDoubleDTO> call = statsWebServiceAPI.getTotalMoneySavedForUser(token, username);
        call.enqueue(new Callback<StatsResDoubleDTO>() {
            @Override
            public void onResponse(@NonNull Call<StatsResDoubleDTO> call, @NonNull Response<StatsResDoubleDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    double moneySaved = response.body().getData();
                    callbackHandler.updateMoneyVal(moneySaved);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatsResDoubleDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void getUserPoints(String username, PointsCallbackHandler callbackHandler) {
        Call<StatsResDoubleDTO> call = statsWebServiceAPI.getUserPoints(token, username);
        call.enqueue(new Callback<StatsResDoubleDTO>() {
            @Override
            public void onResponse(@NonNull Call<StatsResDoubleDTO> call, @NonNull Response<StatsResDoubleDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    double points = response.body().getData();
                    callbackHandler.updatePoints(points);
                }
            }
            @Override
            public void onFailure(@NonNull Call<StatsResDoubleDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
    public void getUserLevel(String username, LevelCallbackHandler callbackHandler) {
        Call<LevelResDTO> call = statsWebServiceAPI.getUserLevel(token, username);
        call.enqueue(new Callback<LevelResDTO>() {
            @Override
            public void onResponse(@NonNull Call<LevelResDTO> call, @NonNull Response<LevelResDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String level = response.body().getData();
                    callbackHandler.updateLevel(level);
                }
            }
            @Override
            public void onFailure(@NonNull Call<LevelResDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void getDateOfLastTransaction(String username, TimeOfLastTransactionCallbackHandler callbackHandler) {
        Call<String> call = statsWebServiceAPI.getDateOfLastTransaction(token, username);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String timeOfLastTransaction = response.body();
                    callbackHandler.updateTimeOfLastTransaction(timeOfLastTransaction);
                } else {
                    callbackHandler.updateTimeOfLastTransaction("-");
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
}
