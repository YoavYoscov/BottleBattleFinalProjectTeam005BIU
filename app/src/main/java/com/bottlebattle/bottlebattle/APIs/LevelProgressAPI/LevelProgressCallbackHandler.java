package com.bottlebattle.bottlebattle.APIs.LevelProgressAPI;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bottlebattle.bottlebattle.R;

import kotlin.reflect.KVisibility;

public class LevelProgressCallbackHandler {
    private final int intermediate_level_threshold = 200;
    private final int expert_level_threshold = 300;
    private final int wizard_level_threshold = 400;
    private final int master_level_threshold = 500;

    private final Context context;

    public LevelProgressCallbackHandler(Context context) {
        this.context = context;
    }

    public void onSuccess(int userLevel, int userPoints) {
        displayCurrentLevelDetails(userLevel);
        if (userLevel < 5) {
            displayNextLevelDetails(userLevel + 1, userPoints);
        }
    }

    private void displayCurrentLevelDetails(int currLevel) {
        TextView levelBubbleTV = null;
        TextView youAreHereTV = null;
        switch(currLevel) {
            case 0: //if curr level is 0 (err data in server), treat it as 1
            case 1:
                levelBubbleTV = ((Activity) context).findViewById(R.id.noviceRecyclerTV);
                youAreHereTV = ((Activity) context).findViewById(R.id.currLevelNoviceTV);
                break;
            case 2:
                //inside a FL, so first make the FL visible:
                ((Activity) context).findViewById(R.id.intermediateRecyclerFL).setVisibility(View.VISIBLE);
                // than, assign vars to layouts:
                levelBubbleTV = ((Activity) context).findViewById(R.id.intermediateRecyclerTV);
                youAreHereTV = ((Activity) context).findViewById(R.id.currLevelIntermediateTV);
                break;
            case 3:
                levelBubbleTV = ((Activity) context).findViewById(R.id.expertRecyclerTV);
                youAreHereTV = ((Activity) context).findViewById(R.id.currLevelExpertTV);
                break;
            case 4:
                //inside a FL, so first make the FL visible:
                ((Activity) context).findViewById(R.id.recyclingWizardFL).setVisibility(View.VISIBLE);
                // than, assign vars to layouts:
                levelBubbleTV = ((Activity) context).findViewById(R.id.recyclingWizardTV);
                youAreHereTV = ((Activity) context).findViewById(R.id.currLevelWizardTV);
                break;
            case 5:
                levelBubbleTV = ((Activity) context).findViewById(R.id.recyclingMasterTV);
                youAreHereTV = ((Activity) context).findViewById(R.id.currLevelMasterTV);
                break;
            default:
                // do nothing
        }
        if (levelBubbleTV != null && youAreHereTV != null) {
            levelBubbleTV.setVisibility(View.VISIBLE);
            youAreHereTV.setVisibility(View.VISIBLE);
            youAreHereTV.setTextColor(ActivityCompat.getColor(context, R.color.success_feedback));
        }
    }

    private void displayNextLevelDetails(int nextLevel, int userPoints) {
        TextView levelBubbleTV = null;
        TextView nextLevelPointsDiffTV = null;
        int pointsDiff = 0;
        switch(nextLevel) {
            case 1: //if curr level is 0 (err data in server), treat it as curr=1, next=2
            case 2:
                //inside a FL, so first make the FL visible:
                ((Activity) context).findViewById(R.id.intermediateRecyclerFL).setVisibility(View.VISIBLE);
                //than, assign vars to layouts:
                levelBubbleTV = ((Activity) context).findViewById(R.id.intermediateRecyclerTV);
                nextLevelPointsDiffTV = ((Activity) context).findViewById(R.id.currLevelIntermediateTV);
                pointsDiff = intermediate_level_threshold - userPoints;
                break;
            case 3:
                levelBubbleTV = ((Activity) context).findViewById(R.id.expertRecyclerTV);
                nextLevelPointsDiffTV = ((Activity) context).findViewById(R.id.currLevelExpertTV);
                pointsDiff = expert_level_threshold - userPoints;
                break;
            case 4:
                //inside a FL, so first make the FL visible:
                ((Activity) context).findViewById(R.id.recyclingWizardFL).setVisibility(View.VISIBLE);
                //than, assign vars to layouts:
                levelBubbleTV = ((Activity) context).findViewById(R.id.recyclingWizardTV);
                nextLevelPointsDiffTV = ((Activity) context).findViewById(R.id.currLevelWizardTV);
                pointsDiff = wizard_level_threshold - userPoints;
                break;
            case 5:
                levelBubbleTV = ((Activity) context).findViewById(R.id.recyclingMasterTV);
                nextLevelPointsDiffTV = ((Activity) context).findViewById(R.id.currLevelMasterTV);
                pointsDiff = master_level_threshold - userPoints;
                break;
            default:
                // do nothing
        }
        if (levelBubbleTV != null && nextLevelPointsDiffTV != null) {
            levelBubbleTV.setVisibility(View.VISIBLE);
            String nextLevelStr = "Next level in\n" + pointsDiff + " points";
            nextLevelPointsDiffTV.setText(nextLevelStr);
            nextLevelPointsDiffTV.setTextColor(ActivityCompat.getColor(context, R.color.success_feedback));
            nextLevelPointsDiffTV.setVisibility(View.VISIBLE);
        }
    }
}
