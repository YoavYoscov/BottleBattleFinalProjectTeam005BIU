package com.bottlebattle.bottlebattle.Activities.UserRankingsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.LeaderboardsAPI.LeaderboardsRepository;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;
import com.bottlebattle.bottlebattle.R;

import java.util.ArrayList;

public class UserRankingsActivity extends AppCompatActivity {
    private final int BTN_BAR_LEN = 3;
    private TextView userRankingsTV;
    private TextView rankingsHeadlineTV;
    private LinearLayout btnBarForTable;
    private Button countryBtn;
    private Button cityBtn;
    int currPressedInBtnBar = 0; //index of currently pressed button (on start, country is pressed by default)
    private Button friendsBtn;
    private TextView userColumn;
    private TextView pointsColumn;
    private RecyclerView tableContentRV;
    private TableAdapter tableAdapter;
    private TableScrollHandler tableScrollHandler;
    private ImageButton refreshBtn;
    private TextView top3TV;
    private TextView secondPlaceField;
    private TextView firstsPlaceField;
    private TextView ThirdPlaceField;
    private TextView secondPlaceVal;
    private TextView firstPlaceVal;
    private TextView thirdPlaceVal;
    private Top3Updater top3Updater;
    private String username;
    private LeaderboardsRepository leaderboardsRepository;
    private ObjectAnimator refreshBtnAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rankings);
        assignVarsToLayouts();
        setFonts();
        disableClickOnLoading();
        addFunctionalities();
    }
    private void assignVarsToLayouts() {
        userRankingsTV = findViewById(R.id.userRankingsTV);
        rankingsHeadlineTV = findViewById(R.id.rankingsHeadlineTV);
        btnBarForTable = findViewById(R.id.btnBarForTable);
        countryBtn = findViewById(R.id.countryBtn);
        cityBtn = findViewById(R.id.cityBtn);
        friendsBtn = findViewById(R.id.friendsBtn);
        userColumn = findViewById(R.id.userColumn);
        pointsColumn = findViewById(R.id.pointsColumn);
        tableContentRV = findViewById(R.id.tableContentRV);
        refreshBtn = findViewById(R.id.refreshBtn);
        top3TV = findViewById(R.id.top3TV);
        secondPlaceField = findViewById(R.id.secondPlaceField);
        firstsPlaceField = findViewById(R.id.firstsPlaceField);
        ThirdPlaceField = findViewById(R.id.ThirdPlaceField);
        secondPlaceVal = findViewById(R.id.secondPlaceVal);
        firstPlaceVal = findViewById(R.id.firstPlaceVal);
        thirdPlaceVal = findViewById(R.id.thirdPlaceVal);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        userRankingsTV.setTypeface(productSansFont);
        rankingsHeadlineTV.setTypeface(productSansFont);
        countryBtn.setTypeface(productSansFont);
        cityBtn.setTypeface(productSansFont);
        friendsBtn.setTypeface(productSansFont);
        userColumn.setTypeface(productSansFont);
        pointsColumn.setTypeface(productSansFont);
        top3TV.setTypeface(productSansFont);
        secondPlaceField.setTypeface(productSansFont);
        firstsPlaceField.setTypeface(productSansFont);
        ThirdPlaceField.setTypeface(productSansFont);
        secondPlaceVal.setTypeface(productSansFont);
        firstPlaceVal.setTypeface(productSansFont);
        thirdPlaceVal.setTypeface(productSansFont);
    }

    private void retrieveUsernameFromIntent() {
        Intent i = getIntent();
        username = i.getStringExtra("username");
    }

    private void setLeaderboardsRepository() {
        Intent i = getIntent();
        String token = i.getStringExtra("token");
        String baseServerDomain = i.getStringExtra("baseServerDomain");
        leaderboardsRepository = new LeaderboardsRepository(baseServerDomain, token, username);
    }

    private void disableClickOnLoading() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void addFunctionalities() {
        setRefreshBtnAnimator();
        retrieveUsernameFromIntent();
        setLeaderboardsRepository();
        setTableAdapter();
        setTableScrollHandler();
        setTop3Updater();
        setClickListenersForBtnBar();
        updateLeaderboards(true, true, false, false, false, false);
        addRefreshTablesFunctionality();
    }

    private void setTableAdapter() {
        tableContentRV.setLayoutManager(new LinearLayoutManager(this));
        tableAdapter = new TableAdapter(this, leaderboardsRepository.getCountryUsersList(), this.username);
        tableContentRV.setAdapter(tableAdapter);
    }

    private void setTableScrollHandler(){
        tableScrollHandler = new TableScrollHandler(username, tableContentRV);
    }

    private void setTop3Updater() {
        top3Updater = new Top3Updater(firstPlaceVal, secondPlaceVal, thirdPlaceVal);
    }

    private void setClickListenersForBtnBar() {
        for (int i = 0; i < BTN_BAR_LEN; i ++) {
            Button btn = (Button) btnBarForTable.getChildAt(i);
            int index = i; //copying i to an (effectively) final variable to be accessed in lambda expression
            btn.setOnClickListener(v -> clickListenerForBtnBar(btn, index));
        }
    }

    private void clickListenerForBtnBar(Button pressedBtn, int indexOfPressedButton) {
        if (indexOfPressedButton == currPressedInBtnBar) {
            return; //if user clicks the button that is already clicked, do nothing
        }
        //otherwise,un-click previous pressed button:
        int grayColorID = ActivityCompat.getColor(this, R.color.menu_item_settings_text);
        ((Button) (btnBarForTable.getChildAt(currPressedInBtnBar))).setTextColor(grayColorID);
        // then click new one:
        int blackColorID = ActivityCompat.getColor(this, R.color.black);
        pressedBtn.setTextColor(blackColorID);
        currPressedInBtnBar = indexOfPressedButton; //update index of currently pressed btn
        //finally, update table and top3 accordingly:
        ArrayList<UserDetailsDTO> usersList = getListByIndex(indexOfPressedButton); //the new list to be displayed
        tableAdapter.updateTable(usersList);
        tableScrollHandler.scrollToUserPosition(usersList);
        top3Updater.updateTop3(usersList);
    }

    private ArrayList<UserDetailsDTO> getListByIndex(int i) {
        if (i == 0) {
            return leaderboardsRepository.getCountryUsersList();
        } else if (i==1) {
            return leaderboardsRepository.getCityUsersList();
        } else return leaderboardsRepository.getFriendsUsersList();
    }

    private void updateLeaderboards(boolean displayInTableCountry, boolean updateTop3Country,
                                    boolean displayInTableCity, boolean updateTop3City,
                                    boolean displayInTableFriends, boolean updateTop3Friends) {
        leaderboardsRepository.getUserCountryLeaderboard(this, tableContentRV, tableAdapter,
                firstPlaceVal, secondPlaceVal, thirdPlaceVal, displayInTableCountry, updateTop3Country, refreshBtnAnimator);
        leaderboardsRepository.getUserCityLeaderboard(this, tableContentRV, tableAdapter,
                firstPlaceVal, secondPlaceVal, thirdPlaceVal,displayInTableCity, updateTop3City, refreshBtnAnimator);
        leaderboardsRepository.getUserFriendsLeaderboard(this, tableContentRV, tableAdapter,
                firstPlaceVal, secondPlaceVal, thirdPlaceVal,displayInTableFriends, updateTop3Friends, refreshBtnAnimator);
    }

    private void addRefreshTablesFunctionality() {
        refreshBtn.setOnClickListener(v -> {
            refreshBtnFunctionality(); //update data in all 3 tables
            //rotate button while loading the data for displayed table:
            refreshBtnAnimator.setDuration(1000); // Duration for one full rotation
            refreshBtnAnimator.setRepeatCount(ObjectAnimator.INFINITE); // Repeat indefinitely
            refreshBtnAnimator.setRepeatMode(ObjectAnimator.RESTART); // Restart the animation after each cycle
            refreshBtnAnimator.start();
            disableClickOnLoading(); //disable clicks while data is fetched (avoid cases where user switch table before data is updated in UI, etc.).
        });
    }

    private void setRefreshBtnAnimator() {
        refreshBtnAnimator = ObjectAnimator.ofFloat(refreshBtn, "rotation", 0f, 360f);
    }

    private void refreshBtnFunctionality() {
        boolean displayInTableCountry = false, updateTop3Country = false,
                displayInTableCity = false, updateTop3City = false,
                displayInTableFriends = false, updateTop3Friends = false;
        if (currPressedInBtnBar == 0) {
            //country table is displayed and should be updated (also) in UI:
            displayInTableCountry = true;
            updateTop3Country = true;
        } else if (currPressedInBtnBar == 1) {
            //city table is displayed and should be updated (also) in UI:
            displayInTableCity = true;
            updateTop3City = true;
        } else {
            //friends table is displayed and should be updated (also) in UI:
            displayInTableFriends = true;
            updateTop3Friends = true;
        }
        updateLeaderboards(displayInTableCountry, updateTop3Country, displayInTableCity, updateTop3City,
                displayInTableFriends, updateTop3Friends);
    }
}