package com.bottlebattle.bottlebattle.APIs.StatsAPI;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class PlasticCallbackHandler extends AbstractCallBackHandler {

    public PlasticCallbackHandler(TextView plasticValTV, AtomicInteger barrierForProgressBar, ProgressBar statsProgressBar) {
        super(plasticValTV, barrierForProgressBar, statsProgressBar);
    }

    public void updatePlasticVal(double plasticSaved) {
        String plasticSavedSTR = plasticSaved + " g";
        this.textView.setText(plasticSavedSTR);
        updateProgressBarIfLast();
    }

}
