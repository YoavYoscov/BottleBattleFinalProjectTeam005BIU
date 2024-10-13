package com.bottlebattle.bottlebattle.APIs.StatsAPI;

import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.concurrent.atomic.AtomicInteger;


public class StatsRepository {
    private final int NUM_OF_STATS = 5;
    private final StatsAPI statsAPI;
    private final String username;

    public StatsRepository(String baseServerAddress, String token, String username) {
        this.statsAPI = new StatsAPI(baseServerAddress, token);
        this.username = username;
    }
    private void getTotalPlasticSavedForUser(TextView plasticValTV, AtomicInteger barrierForProgressBar,
                                             ProgressBar statsProgressBar) {
        PlasticCallbackHandler callbackHandler = new PlasticCallbackHandler(plasticValTV, barrierForProgressBar, statsProgressBar);
        statsAPI.getTotalPlasticSavedForUser(username, callbackHandler);
    }
    private void getTotalMoneySavedForUser(TextView moneyValTV, AtomicInteger barrierForProgressBar,
                                           ProgressBar statsProgressBar) {
        MoneyCallbackHandler callbackHandler = new MoneyCallbackHandler(moneyValTV, barrierForProgressBar, statsProgressBar);
        statsAPI.getTotalMoneySavedForUser(username, callbackHandler);
    }
    private void getUserPoints(TextView pointsValTV, AtomicInteger barrierForProgressBar,
                               ProgressBar statsProgressBar) {
        PointsCallbackHandler callbackHandler = new PointsCallbackHandler(pointsValTV, barrierForProgressBar, statsProgressBar);
        statsAPI.getUserPoints(username, callbackHandler);
    }
    private void getUserLevel(TextView levelValTV, AtomicInteger barrierForProgressBar,
                              ProgressBar statsProgressBar) {
        LevelCallbackHandler callbackHandler = new LevelCallbackHandler(levelValTV, barrierForProgressBar, statsProgressBar);
        statsAPI.getUserLevel(username, callbackHandler);
    }
    private void getDateOfLastTransaction(TextView timeValTV, AtomicInteger barrierForProgressBar,
                                          ProgressBar statsProgressBar) {
        TimeOfLastTransactionCallbackHandler callbackHandler =
                new TimeOfLastTransactionCallbackHandler(timeValTV, barrierForProgressBar, statsProgressBar);
        statsAPI.getDateOfLastTransaction(username, callbackHandler);
    }

    public void updateStats(TextView plasticValTV, TextView  moneyValTV, TextView  pointsValTV,
                            TextView  levelValTV, TextView  timeValTV, ProgressBar statsProgressBar) {
        AtomicInteger barrierForProgressBar = new AtomicInteger();
        barrierForProgressBar.set(NUM_OF_STATS);
        getTotalPlasticSavedForUser(plasticValTV, barrierForProgressBar, statsProgressBar);
        getTotalMoneySavedForUser(moneyValTV, barrierForProgressBar, statsProgressBar);
        getUserPoints(pointsValTV, barrierForProgressBar, statsProgressBar);
        getUserLevel(levelValTV, barrierForProgressBar, statsProgressBar);
        getDateOfLastTransaction(timeValTV, barrierForProgressBar, statsProgressBar);
    }
}
