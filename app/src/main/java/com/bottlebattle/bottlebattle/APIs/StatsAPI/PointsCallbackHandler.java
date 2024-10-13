package com.bottlebattle.bottlebattle.APIs.StatsAPI;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class PointsCallbackHandler extends AbstractCallBackHandler {
    public PointsCallbackHandler(TextView pointsValTV, AtomicInteger barrierForProgressBar, ProgressBar statsProgressBar) {
        super(pointsValTV, barrierForProgressBar, statsProgressBar);
    }

    public void updatePoints(double points){
        int pointsRounded = (int) points;
        this.textView.setText(Integer.toString(pointsRounded));
        updateProgressBarIfLast();
    }
}
