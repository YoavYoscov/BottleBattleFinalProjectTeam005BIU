package com.bottlebattle.bottlebattle.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.TokenAPI.GoogleSignInRequestDTO;
import com.bottlebattle.bottlebattle.APIs.TokenAPI.TokenRepository;
import com.bottlebattle.bottlebattle.APIs.citiesAPI.CitiesRepository;
import com.bottlebattle.bottlebattle.R;

public class GetCityActivity extends AppCompatActivity {
    private String username;
    private String token;
    private String profilePic;
    private String fireBaseToken;
    private String baseServerDomain;
    private TextView welcomeTxt;
    private AutoCompleteTextView city;
    private Button submitCityBTN;
    private TextView updatingTxt;
    private CitiesRepository citiesRepository;
    private TokenRepository tokenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_city);
        assignVarsToLayouts();
        setFonts();
        retrieveDetailsFromIntent();
        setTokenRepository();
        addFunctionalities();
        displayWelcomeText();
    }

    private void assignVarsToLayouts() {
        welcomeTxt = findViewById(R.id.welcomeTxt);
        city = findViewById(R.id.city2);
        submitCityBTN = findViewById(R.id.submitCityBTN);
        updatingTxt = findViewById(R.id.updateTxt);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        welcomeTxt.setTypeface(productSansFont);
        city.setTypeface(productSansFont);
        submitCityBTN.setTypeface(productSansFont);
        updatingTxt.setTypeface(productSansFont);
    }


    private void retrieveDetailsFromIntent() {
        Intent i = getIntent();
        username = i.getStringExtra("username");
        token = i.getStringExtra("token"); //jwt token
        profilePic = i.getStringExtra("profilePic");
        fireBaseToken = i.getStringExtra("fireBaseToken");
        baseServerDomain = i.getStringExtra("baseServerDomain");
    }

    private void setTokenRepository() {
        tokenRepository = new TokenRepository(baseServerDomain);
    }

    private void addFunctionalities() {
        setCityDropDownList();
        enableSubmission(); //if city is valid, enable submit btn functionality, else disable it
        submitCityBTN.setOnClickListener(view -> { //enabled only if current city is valid
            updatingTxt.setVisibility(View.VISIBLE);
            String citySTR = city.getText().toString();
            GoogleSignInRequestDTO googleSignInRequest = new GoogleSignInRequestDTO(token, profilePic, fireBaseToken, citySTR);
            tokenRepository.googleSignIn(googleSignInRequest, username,this); //sign user in and display home screen
        });
    }

    private void setCityDropDownList() {
        citiesRepository = new CitiesRepository();
        citiesRepository.getCitiesInIsrael(this, city);
    }

    private void enableSubmission() {
        Context context = this;
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (citiesRepository.getCities().contains(city.getText().toString())) {
                    submitCityBTN.setEnabled(true);
                    submitCityBTN.setBackground(ContextCompat.getDrawable(context, R.drawable.round_button));
                } else {
                    submitCityBTN.setEnabled(false);
                    submitCityBTN.setBackground(ContextCompat.getDrawable(context, R.drawable.round_button_disabled));
                }
            }
        });
    }

    private void displayWelcomeText() {
        String welcomeMessage = "Hey " + username + ",\n" + "Welcome to Bottle Battle!\n";
        welcomeTxt.setText(welcomeMessage);
    }
}