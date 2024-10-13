package com.bottlebattle.bottlebattle.APIs.StatsAPI;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class LevelCallbackHandler extends AbstractCallBackHandler{

    public LevelCallbackHandler(TextView levelValTV, AtomicInteger barrierForProgressBar, ProgressBar statsProgressBar) {
        super(levelValTV, barrierForProgressBar, statsProgressBar);
    }
    public void updateLevel(String level) {
        level = level.trim(); //remove redundant spaces (if any)
        //if level includes 2 words (hence there is a space in the middle of it), change space to '\n':
        int indexOfSpace = level.indexOf(' ');
        if (indexOfSpace != -1) { //if there is a space in the level name
            level = level.substring(0, indexOfSpace) + '\n' + level.substring(indexOfSpace+1);
        }
        this.textView.setText(level);
        updateProgressBarIfLast();
    }
}
