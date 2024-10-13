package com.bottlebattle.bottlebattle.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI.RecycleItemsRepository;
import com.bottlebattle.bottlebattle.R;

public class ItemRecycledActivity extends AppCompatActivity {
    private TextView successMessageTV;
    private ScrollView notesSV;
    private TextView notesTV;
    private TextView suitableRecycleBinTV;
    private ImageView recycleBinIV;
    private TextView recycleBinColorTV;
    private TextView alternativeTV;
    private Button continueBtn;
    private ProgressBar itemRecycledProgressBar;
    private RecycleItemsRepository recycleItemsRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_recycled);
        assignVarsToLayouts();
        setFonts();
        setRecycleItemsRepository();
        displayNotesAndSuggestions();
        displaySuitableRecycleBin(getIntent().getStringExtra("suitableRecycleBin"));
/*
        displayBetterAlternativeProductIfAny();
*/
        setClickListenerForBtn();
    }
    private void assignVarsToLayouts() {
        successMessageTV = findViewById(R.id.successMessageTV);
        notesSV = findViewById(R.id.notesSV);
        notesTV = findViewById(R.id.notesTV);
        suitableRecycleBinTV = findViewById(R.id.suitableRecycleBinTV);
        recycleBinIV = findViewById(R.id.recycleBinIV);
        recycleBinColorTV = findViewById(R.id.recycleBinColorTV);
        alternativeTV = findViewById(R.id.alternativeTV);
        continueBtn = findViewById(R.id.continueBtn);
        itemRecycledProgressBar = findViewById(R.id.itemRecycledProgressBar);
    }
    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        successMessageTV.setTypeface(productSansFont);
        notesTV.setTypeface(productSansFont);
        suitableRecycleBinTV.setTypeface(productSansFont);
        recycleBinColorTV.setTypeface(productSansFont);
        alternativeTV.setTypeface(productSansFont);
        continueBtn.setTypeface(productSansFont);
    }

    private void setRecycleItemsRepository() {
        Intent i = getIntent();
        String token = i.getStringExtra("token");
        String baseServerDomain = i.getStringExtra("baseServerDomain");
        recycleItemsRepository = new RecycleItemsRepository(baseServerDomain, token, null);
    }

    private void displayNotesAndSuggestions() {
        String recycleItemId = getIntent().getStringExtra("recycleItemId");
        recycleItemsRepository.getNotesAndSuggestions(recycleItemId, notesTV, notesSV, itemRecycledProgressBar);
    }
    private void displaySuitableRecycleBin(String suitableRecycleBin) {
        Drawable recycleBinImg;
        int color, recycleBinStrId = R.string.green;
        switch(suitableRecycleBin) {
            case "blue":
                recycleBinImg = ActivityCompat.getDrawable(this, R.drawable.recycle_bin_blue);
                color = ActivityCompat.getColor(this, R.color.recycle_bin_blue);
                recycleBinStrId = R.string.blue;
                break;
            case "orange":
                recycleBinImg = ActivityCompat.getDrawable(this, R.drawable.recycle_bin_orange);
                color = ActivityCompat.getColor(this, R.color.recycle_bin_orange);
                recycleBinStrId = R.string.orange;
                break;
            case "purple":
                recycleBinImg = ActivityCompat.getDrawable(this, R.drawable.recycle_bin_purple);
                color = ActivityCompat.getColor(this, R.color.recycle_bin_purple);
                recycleBinStrId = R.string.purple;
                break;
            case "cardboardRecyclingContainer":
                recycleBinImg = ActivityCompat.getDrawable(this, R.drawable.recycle_bin_cardboard);
                color = ActivityCompat.getColor(this, R.color.recycle_bin_cardboard);
                recycleBinStrId = R.string.cardboardRecyclingContainer;
                break;
            case "refundable":
                suitableRecycleBinTV.setText(R.string.greate_news);
                recycleBinImg = ActivityCompat.getDrawable(this, R.drawable.money_svg_refundable);
                color = ActivityCompat.getColor(this, R.color.recycle_bin_refundable);
                recycleBinStrId = R.string.refundable;
                break;
            case "green":
                suitableRecycleBinTV.setVisibility(View.GONE);
                recycleBinImg = ActivityCompat.getDrawable(this, R.drawable.recycle_bin_green);
                color = ActivityCompat.getColor(this, R.color.recycle_bin_green);
                recycleBinStrId = R.string.not_recyclable_item;
                break;
            default:
                //shouldn't get here, but just in case
                recycleBinImg = ActivityCompat.getDrawable(this, R.drawable.recycle_bin_green);
                color = ActivityCompat.getColor(this, R.color.recycle_bin_green);
        }
        recycleBinIV.setImageDrawable(recycleBinImg);
        recycleBinColorTV.setText(recycleBinStrId);
        recycleBinColorTV.setTextColor(color);
    }

/*    private void displayBetterAlternativeProductIfAny() {
        recycleItemsAPI.getBetterAlternativeIfSuchExists(token, recycleItemId, alternativeTV);
    }*/
    private void setClickListenerForBtn() {
        continueBtn.setOnClickListener(v -> finish());
    }
}