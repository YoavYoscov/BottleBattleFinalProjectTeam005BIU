package com.bottlebattle.bottlebattle.APIs.StatsAPI;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MoneyCallbackHandler extends AbstractCallBackHandler {
    public MoneyCallbackHandler(TextView moneyValTV, AtomicInteger barrierForProgressBar, ProgressBar statsProgressBar) {
        super(moneyValTV, barrierForProgressBar, statsProgressBar);
    }

    public void updateMoneyVal(double moneySaved) {
        int moneySavedRounded = (int) moneySaved;
        String moneySavedSTR = moneySavedRounded + " ILS";
        this.textView.setText(moneySavedSTR);
        updateProgressBarIfLast();
    }
}
