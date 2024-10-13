package com.bottlebattle.bottlebattle.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.addFriendsAPI.AddFriendsRepository;
import com.bottlebattle.bottlebattle.R;

public class AddFriendsActivity extends AppCompatActivity {
    private TextView addFriendsTV;
    private TextView expandNetworkTV;
    private TextView screenDescTV;
    private AutoCompleteTextView addFriendsACTV;
    private TextView addFriendsFeedback;
    private Button addFriendsBtn;
    private AddFriendsRepository addFriendsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        assignVarsToLayouts();
        setFonts();
        disableClicksOnLoading();
        setAddFriendsRepository();
        updateFriendsList();
        addFunctionalities();
    }

    private void assignVarsToLayouts() {
        addFriendsTV = findViewById(R.id.addFriendsTV);
        expandNetworkTV = findViewById(R.id.expandNetworkTV);
        screenDescTV = findViewById(R.id.screenDescTV);
        addFriendsACTV = findViewById(R.id.addFriendsACTV);
        addFriendsFeedback = findViewById(R.id.addFriendsFeedback);
        addFriendsBtn = findViewById(R.id.addFriendsBtn);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        addFriendsTV.setTypeface(productSansFont);
        expandNetworkTV.setTypeface(productSansFont);
        screenDescTV.setTypeface(productSansFont);
        addFriendsACTV.setTypeface(productSansFont);
        addFriendsFeedback.setTypeface(productSansFont);
        addFriendsBtn.setTypeface(productSansFont);
    }

    private void disableClicksOnLoading() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void setAddFriendsRepository() {
        Intent i = getIntent();
        String token = i.getStringExtra("token"),
                baseServerDomain = i.getStringExtra("baseServerDomain"),
                username = i.getStringExtra("username");
        addFriendsRepository = new AddFriendsRepository(token, baseServerDomain, username);
    }

    private void updateFriendsList() {
        addFriendsRepository.updateFriendsList(this, addFriendsACTV);
    }

    private void addFunctionalities() {
        handleInputChange();
        setClickListenerForAddBtn();
    }

    private void handleInputChange() {
        addFriendsACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                addFriendsFeedback.setText(""); //clear previous feedback message when user types
                String input = addFriendsACTV.getText().toString();
                if (isInputValid(input)) {
                    enableAddBtn();
                } else {
                    disableAddBtn();
                }
            }
        });
    }

    private boolean isInputValid(String input) {
        return addFriendsRepository.getAllUsernames().contains(input);
    }

    private void enableAddBtn() {
        Drawable enabledBtnBG = ActivityCompat.getDrawable(this, R.drawable.round_button);
        addFriendsBtn.setBackground(enabledBtnBG);
        addFriendsBtn.setEnabled(true);
    }

    private void disableAddBtn() {
        Drawable disabledBtnBG = ActivityCompat.getDrawable(this, R.drawable.round_button_disabled);
        addFriendsBtn.setBackground(disabledBtnBG);
        addFriendsBtn.setEnabled(false);
    }

    private void setClickListenerForAddBtn() {
        addFriendsBtn.setOnClickListener(view -> {
            String friendUsername = addFriendsACTV.getText().toString();
            addFriendsRepository.addFriendByUsername(this, friendUsername, addFriendsFeedback, addFriendsACTV);
        });
    }
}