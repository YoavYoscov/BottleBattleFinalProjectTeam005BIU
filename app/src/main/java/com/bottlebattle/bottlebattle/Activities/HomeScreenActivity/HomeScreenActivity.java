package com.bottlebattle.bottlebattle.Activities.HomeScreenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserRepository;
import com.bottlebattle.bottlebattle.Activities.HomeScreenActivity.Menu.MenuAdapter;
import com.bottlebattle.bottlebattle.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeScreenActivity extends AppCompatActivity {
    private final int MENU_LENGTH = 7;
    private TextView home;
    private CircleImageView profilePic;
    private TextView hello;
    private String username;
    private TextView usernameHomeScreen;
    private LinearLayout menuDots;
    private ViewPager2 menuViewPager;
    private TextView ranksText;
    private TextView tableLevelCategory;
    private TextView tableLevelValue;
    private TextView tablePointsCategory;
    private TextView tablePointsValue;
    private TextView moneyText;
    private TextView moneyAmount;
    private UserRepository userRepository;
    private String token;
    private String baseServerDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        assignVarsToLayouts();
        setFonts();
        retrieveTokenDomainAndUsername();
        setUserRepository();
        updateUserDetails();
        setMenuAdapter();
    }

    private void assignVarsToLayouts() {
        home = findViewById(R.id.home);
        profilePic = findViewById(R.id.profilePic);
        hello = findViewById(R.id.hello);
        usernameHomeScreen = findViewById(R.id.username_homeScreen);
        menuDots = findViewById(R.id.menuDots);
        menuViewPager = findViewById(R.id.menuViewPager);
        ranksText = findViewById(R.id.statusText);
        tableLevelCategory = findViewById(R.id.tableLevelCategory);
        tableLevelValue = findViewById(R.id.tableLevelValue);
        tablePointsCategory = findViewById(R.id.tablePointsCategory);
        tablePointsValue = findViewById(R.id.tablePointsValue);
        moneyText = findViewById(R.id.moneyText);
        moneyAmount = findViewById(R.id.moneyAmount);
    }
    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        Typeface productSansFontBold = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Bold.ttf");
        home.setTypeface(productSansFont);
        hello.setTypeface(productSansFont);
        usernameHomeScreen.setTypeface(productSansFontBold);
        ranksText.setTypeface(productSansFontBold);
        tableLevelCategory.setTypeface(productSansFont);
        tableLevelValue.setTypeface(productSansFont);
        tablePointsCategory.setTypeface(productSansFont);
        tablePointsValue.setTypeface(productSansFont);
        moneyText.setTypeface(productSansFontBold);
        moneyAmount.setTypeface(productSansFont);
    }

    private void retrieveTokenDomainAndUsername() {
        token = "Bearer " + getIntent().getStringExtra("token");
        baseServerDomain = getIntent().getStringExtra("baseServerDomain");
        username = getIntent().getStringExtra("username");
    }
    private void setUserRepository() {
        userRepository = new UserRepository(token, baseServerDomain, username);
    }
    private void setMenuAdapter() {
        MenuAdapter menuAdapter = new MenuAdapter(this, username, token, baseServerDomain);
        menuViewPager.setAdapter(menuAdapter);
        //we make the adapter think there are many (Integer.MAX_VALUE) items in the menu (further explained in MenuAdapter)
        //hence, we start from the position which is closest to the middle position and corresponds to the first item (to enable seemingly infinite scrolling in both directions):
        int startingPosition = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % MENU_LENGTH;
        menuViewPager.setCurrentItem(startingPosition, false);
        menuViewPager.setOffscreenPageLimit(1); // Set offscreen page limit to show adjacent pages (each menu item is a "page")
                                        // it determines how many pages are retained to either side of the currently visible page
        // Set page transformer for carousel effect:
        float adjacentItemsOffset = getResources().getDimension(R.dimen.adjacent_items_visible_wide_screen); //how many pixels of the adjacent items should be visible
        //If the screen is too narrow (not necessarily physically, but dense in width), this offset is too big, so we adjust it:
        int density = getResources().getDisplayMetrics().densityDpi; //get density
        if (density > 460) {
            adjacentItemsOffset = getResources().getDimension(R.dimen.adjacent_items_visible_narrow_screen);
        }
        float offset = adjacentItemsOffset; //copying computed offset to an (effectively) final variable to be used in the lambda expression
        menuViewPager.setPageTransformer((page, position) -> {
            page.setTranslationX(position * -(offset));
            //we want to move the page on the left side of the current page a bit to the right
            // and move the page on the right side of the current page a bit to the left
            // the page on the right has position 1, the page on the left has position -1, and the current (middle) page has position 0
            // hence we multiply adjacentItemsOffset by -1: the offset is negative (to the left) for the item on the right, positive (to the right) for the item on the left, and 0 for the current item
            //also, update menuDots according to scrolling:
            updateMenuDots();
        });
    }
    private void updateMenuDots() {
        int currPosition = menuViewPager.getCurrentItem() % MENU_LENGTH;
        for (int i = 0; i < MENU_LENGTH; i++) {
            if (i == currPosition) {
                darkenDot(i);
            }
            else brightenDot(i);
        }
    }
    private void darkenDot(int position) {
        menuDots.getChildAt(position).setBackgroundResource(R.drawable.menu_dot_dark);
    }
    private void brightenDot(int position) {
        menuDots.getChildAt(position).setBackgroundResource(R.drawable.menu_dot);
    }

    private void updateUserDetails(){
        usernameHomeScreen.setText(username);
        userRepository.retrieveUserDetails(this, moneyAmount, profilePic, tableLevelValue, tablePointsValue);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUserDetails(); //update user details after returning back from a previous screen
    }
}