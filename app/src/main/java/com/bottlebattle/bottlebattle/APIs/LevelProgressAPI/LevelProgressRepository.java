package com.bottlebattle.bottlebattle.APIs.LevelProgressAPI;

import android.content.Context;

public class LevelProgressRepository {
    private final LevelProgressAPI levelProgressAPI;
    private final String username;

    public LevelProgressRepository(String baseServerAddress, String token, String username) {
        this.levelProgressAPI = new LevelProgressAPI(baseServerAddress, token);
        this.username = username;
    }

    public void retrieveUserLevelAndPoints(Context context) {
        LevelProgressCallbackHandler callbackHandler = new LevelProgressCallbackHandler(context);
        levelProgressAPI.getUserByUsername(username, callbackHandler);
    }
}
