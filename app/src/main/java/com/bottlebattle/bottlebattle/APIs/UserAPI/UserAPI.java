package com.bottlebattle.bottlebattle.APIs.UserAPI;
import android.util.Log;
import androidx.annotation.NonNull;
import com.bottlebattle.bottlebattle.APIs.ServerResponseDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private final UserWebServiceAPI userWebServiceAPI;
    private final String baseServerDomain;

    // (GET/POST) and the urls of the requests are defined.
    public UserAPI(String baseServerDomain) {
        //the full url is determined by the concatenation of the base address and the url in the UserWebServiceAPI interface
        //gson is used to parse objects into jsons and vice-versa.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseServerDomain).addConverterFactory(GsonConverterFactory.create()).build();
        userWebServiceAPI = retrofit.create(UserWebServiceAPI.class);
        this.baseServerDomain = baseServerDomain;
    }

    /* request format: GET DOMAIN/api/Users/CheckAvailableUsername/:username
     *  checks if username is available */
    public void checkAvailableUsername(String username, CheckAvailableUsernameCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = userWebServiceAPI.checkAvailableUsername(username);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess();
                } else {
                    //if here, username is already taken (hence display error message from server and set isUsernameValid to false):
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
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void forgotPassword(String username, ForgotPasswordCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = userWebServiceAPI.forgotPassword(username);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess(username, baseServerDomain);
                } else { //some error occurred (username not found, etc.), => display server's err message
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
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    /* request format: POST DOMAIN/api/Users
     * registers a new user */
    public void createUser(CreateUserReqDTO user, CreateUserCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = userWebServiceAPI.createUser(user);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess();
                } else {
                    //if here, username is already taken (hence display error message from server and set isUsernameValid to false):
                    String errorMessage = "An error occurred, please try again later.";
                    callbackHandler.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }

    public void retrieveUserDetails(String token, String username, UserDetailsCallbackHandler callbackHandler) {
        Call<GetUserByUsernameResDTO> call = userWebServiceAPI.getUserByUsername(token, username);
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

    public void resetPassword(ResetPasswordReqDTO resetPasswordReq, ResetPasswordCallbackHandler callbackHandler) {
        Call<ServerResponseDTO> call = userWebServiceAPI.resetPassword(resetPasswordReq);
        call.enqueue(new Callback<ServerResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponseDTO> call, @NonNull Response<ServerResponseDTO> response) {
                if (response.isSuccessful()) {
                    callbackHandler.onSuccess();
                }
                else {
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
            public void onFailure(@NonNull Call<ServerResponseDTO> call, @NonNull Throwable t) {
                Log.e("err", call.request().toString());
            }
        });
    }
}
