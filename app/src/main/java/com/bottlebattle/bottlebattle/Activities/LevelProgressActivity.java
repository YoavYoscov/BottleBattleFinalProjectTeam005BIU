package com.bottlebattle.bottlebattle.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.LevelProgressAPI.LevelProgressRepository;
import com.bottlebattle.bottlebattle.R;
public class LevelProgressActivity extends AppCompatActivity {
    private TextView progressTV;
    private Button noviceRecyclerBtn;
    private TextView noviceRecyclerTV;
    private TextView currLevelNoviceTV;
    private Button intermediateRecyclerBtn;
    private FrameLayout intermediateRecyclerFL;
    private TextView intermediateRecyclerTV;
    private TextView currLevelIntermediateTV;
    private Button expertRecyclerBtn;
    private TextView expertRecyclerTV;
    private TextView currLevelExpertTV;
    private Button recyclingWizardBtn;
    private FrameLayout recyclingWizardFL;
    private TextView recyclingWizardTV;
    private TextView currLevelWizardTV;
    private Button recyclingMasterBtn;
    private TextView recyclingMasterTV;
    private TextView currLevelMasterTV;
    private LevelProgressRepository levelProgressRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_progress);
        assignVarsToLayouts();
        setFonts();
        setRepository();
        retrieveUserLevelAndPoints();
    }

    private void assignVarsToLayouts() {
        progressTV=findViewById(R.id.progressTV);
        noviceRecyclerBtn=findViewById(R.id.noviceRecyclerBtn);
        noviceRecyclerTV=findViewById(R.id.noviceRecyclerTV);
        currLevelNoviceTV=findViewById(R.id.currLevelNoviceTV);
        intermediateRecyclerBtn=findViewById(R.id.intermediateRecyclerBtn);
        intermediateRecyclerFL=findViewById(R.id.intermediateRecyclerFL);
        intermediateRecyclerTV=findViewById(R.id.intermediateRecyclerTV);
        currLevelIntermediateTV=findViewById(R.id.currLevelIntermediateTV);
        expertRecyclerBtn=findViewById(R.id.expertRecyclerBtn);
        expertRecyclerTV=findViewById(R.id.expertRecyclerTV);
        currLevelExpertTV=findViewById(R.id.currLevelExpertTV);
        recyclingWizardBtn=findViewById(R.id.recyclingWizardBtn);
        recyclingWizardFL=findViewById(R.id.recyclingWizardFL);
        recyclingWizardTV=findViewById(R.id.recyclingWizardTV);
        currLevelWizardTV=findViewById(R.id.currLevelWizardTV);
        recyclingMasterBtn=findViewById(R.id.recyclingMasterBtn);
        recyclingMasterTV=findViewById(R.id.recyclingMasterTV);
        currLevelMasterTV=findViewById(R.id.currLevelMasterTV);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        progressTV.setTypeface(productSansFont);
        noviceRecyclerBtn.setTypeface(productSansFont);
        noviceRecyclerTV.setTypeface(productSansFont);
        currLevelNoviceTV.setTypeface(productSansFont);
        intermediateRecyclerBtn.setTypeface(productSansFont);
        intermediateRecyclerTV.setTypeface(productSansFont);
        currLevelIntermediateTV.setTypeface(productSansFont);
        expertRecyclerBtn.setTypeface(productSansFont);
        expertRecyclerTV.setTypeface(productSansFont);
        currLevelExpertTV.setTypeface(productSansFont);
        recyclingWizardBtn.setTypeface(productSansFont);
        recyclingWizardTV.setTypeface(productSansFont);
        currLevelWizardTV.setTypeface(productSansFont);
        recyclingMasterBtn.setTypeface(productSansFont);
        recyclingMasterTV.setTypeface(productSansFont);
        currLevelMasterTV.setTypeface(productSansFont);
    }

    private void setRepository() {
        Intent i = getIntent();
        String username = i.getStringExtra("username"),
                token = i.getStringExtra("token"),
                baseServerDomain = i.getStringExtra("baseServerDomain");
        this.levelProgressRepository = new LevelProgressRepository(baseServerDomain, token, username);
    }

    private void retrieveUserLevelAndPoints() {
        levelProgressRepository.retrieveUserLevelAndPoints(this);
    }
}