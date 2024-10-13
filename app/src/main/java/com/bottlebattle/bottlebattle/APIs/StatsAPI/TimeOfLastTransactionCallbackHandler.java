package com.bottlebattle.bottlebattle.APIs.StatsAPI;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class TimeOfLastTransactionCallbackHandler extends AbstractCallBackHandler {

    public TimeOfLastTransactionCallbackHandler(TextView timeValTV, AtomicInteger barrierForProgressBar, ProgressBar statsProgressBar) {
        super(timeValTV, barrierForProgressBar, statsProgressBar);
    }
    public void updateTimeOfLastTransaction(String timeOfLastTransaction) {
        if (timeOfLastTransaction.equals("-")) {
            this.textView.setText(timeOfLastTransaction);
            updateProgressBarIfLast();
            return;
        } //else:
        timeOfLastTransaction = formatRes(timeOfLastTransaction);
        this.textView.setText(timeOfLastTransaction);
        updateProgressBarIfLast();
    }

    private String formatRes(String timeOfLastTransaction) {
        timeOfLastTransaction = timeOfLastTransaction.trim(); //remove redundant spaces
        //first, remove first part of response (the day, e.g. "Wed"):
        int indexOfFirstSpace = timeOfLastTransaction.indexOf(' ');
        if (indexOfFirstSpace != -1) { //if there is indeed space in server's res
            timeOfLastTransaction = timeOfLastTransaction.substring(indexOfFirstSpace + 1);
        }
        //then, remove last part of response ("GMT+0300"):
        int indexOfLastSpace = timeOfLastTransaction.lastIndexOf(' ');
        if (indexOfLastSpace != -1) { //if there is indeed space in server's res
            timeOfLastTransaction = timeOfLastTransaction.substring(0, indexOfLastSpace);
        }
        //now, add enter instead of last space in remaining string:
        int indexOfLastSpace2 = timeOfLastTransaction.lastIndexOf(' ');
        if (indexOfLastSpace2 != -1) { //if there is indeed space in server's res
            timeOfLastTransaction = timeOfLastTransaction.substring(0, indexOfLastSpace2) + '\n' + timeOfLastTransaction.substring(indexOfLastSpace2 + 1);
        }
        return timeOfLastTransaction;
    }
}
