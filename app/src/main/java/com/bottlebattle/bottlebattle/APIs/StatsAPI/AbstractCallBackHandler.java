package com.bottlebattle.bottlebattle.APIs.StatsAPI;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractCallBackHandler {
    protected final TextView textView;
    private final AtomicInteger barrierForProgressBar;
    private final ProgressBar statsProgressBar;

    public AbstractCallBackHandler(TextView textView, AtomicInteger barrierForProgressBar, ProgressBar statsProgressBar) {
        this.textView = textView;
        this.barrierForProgressBar = barrierForProgressBar;
        this.statsProgressBar = statsProgressBar;
    }

    protected void updateProgressBarIfLast() {
        //decrement barrier (implemented with decreasing semaphore). If val is 0, clear progress bar.
        int currentValue = barrierForProgressBar.decrementAndGet();
        if (currentValue == 0) {
            statsProgressBar.setVisibility(View.GONE);
        }
    }
}
