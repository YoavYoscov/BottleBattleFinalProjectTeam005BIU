package com.bottlebattle.bottlebattle.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.StatsAPI.StatsRepository;
import com.bottlebattle.bottlebattle.R;

public class StatsActivity extends AppCompatActivity {
    private TextView userStatisticsTV;
    private TextView yourStatsTV;
    private TextView plasticCategoryTV;
    private TextView plasticValTV;
    private TextView timeCategoryTV;
    private TextView timeValTV;
    private TextView pointsCategoryTV;
    private TextView pointsValTV;
    private TextView levelCategoryTV;
    private TextView levelValTV;
    private TextView moneyCategoryTV;
    private TextView moneyValTV;
    private ProgressBar statsProgressBar;
    private StatsRepository statsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        assignVarsToLayouts();
        setFonts();
        setRepository();
        updateStats();
    }

    private void assignVarsToLayouts() {
        userStatisticsTV = findViewById(R.id.userStatisticsTV);
        yourStatsTV = findViewById(R.id.yourStatsTV);
        plasticCategoryTV = findViewById(R.id.plasticCategoryTV);
        plasticValTV = findViewById(R.id.plasticValTV);
        timeCategoryTV = findViewById(R.id.timeCategoryTV);
        timeValTV = findViewById(R.id.timeValTV);
        pointsCategoryTV = findViewById(R.id.pointsCategoryTV);
        pointsValTV = findViewById(R.id.pointsValTV);
        levelCategoryTV = findViewById(R.id.levelCategoryTV);
        levelValTV = findViewById(R.id.levelValTV);
        moneyCategoryTV = findViewById(R.id.moneyCategoryTV);
        moneyValTV = findViewById(R.id.moneyValTV);
        statsProgressBar = findViewById(R.id.statsProgressBar);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        userStatisticsTV.setTypeface(productSansFont);
        yourStatsTV.setTypeface(productSansFont);
        plasticCategoryTV.setTypeface(productSansFont);
        plasticValTV.setTypeface(productSansFont);
        timeCategoryTV.setTypeface(productSansFont);
        timeValTV.setTypeface(productSansFont);
        pointsCategoryTV.setTypeface(productSansFont);
        pointsValTV.setTypeface(productSansFont);
        levelCategoryTV.setTypeface(productSansFont);
        levelValTV.setTypeface(productSansFont);
        moneyCategoryTV.setTypeface(productSansFont);
        moneyValTV.setTypeface(productSansFont);
    }

    private void setRepository() {
        Intent i = getIntent();
        String username = i.getStringExtra("username"),
                token = i.getStringExtra("token"),
                baseServerDomain = i.getStringExtra("baseServerDomain");
        this.statsRepository = new StatsRepository(baseServerDomain, token, username);
    }

    private void updateStats() {
        this.statsRepository.updateStats(plasticValTV, moneyValTV, pointsValTV,
                levelValTV, timeValTV, statsProgressBar);
    }
}