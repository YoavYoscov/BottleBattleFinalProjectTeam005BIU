package com.bottlebattle.bottlebattle.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.SettingsAPI.SettingsRepository;
import com.bottlebattle.bottlebattle.APIs.citiesAPI.CitiesRepository;
import com.bottlebattle.bottlebattle.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private ConstraintLayout settingsActivity;
    private TextView settingsTV;
    private LinearLayout settingsBtnBar;
    private Button profilePicSettingsBtnBar;
    private Button citySettingsBtnBar;
    private Button deleteSettingsBtnBar;
    private TextView changeProfilePicTxt;
    private CircleImageView profilePic;
    private TextView newProfilePicFeedback;
    private Button changeProfilePicBtn;
    private TextView changeCityTV;
    private AutoCompleteTextView newCityACTV;
    private TextView newCityFeedback;
    private Button changeCityBtn;
    private TextView deleteAccountTV;
    private TextView deleteAccountInstructionTV;
    private Button deleteBtn;
    private TextView deleteAccountFeedback;
    private SettingsRepository settingsRepository;
    private CitiesRepository citiesRepository;
    private int currentClickedBtn = 0; //0,1,2
    private Bitmap profilePicBitmap = null;
    private String profilePicBase64 = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        assignVarsToLayouts();
        setFonts();
        setRepositories();
        retrieveUserDetails();
        retrieveCities();
        addLocalFunctionalities();
    }

    private void assignVarsToLayouts() {
        settingsActivity = findViewById(R.id.settingsActivity);
        settingsTV = findViewById(R.id.settingsTV);
        settingsBtnBar = findViewById(R.id.settingsBtnBar);
        profilePicSettingsBtnBar = findViewById(R.id.profilePicSettingsBtnBar);
        citySettingsBtnBar = findViewById(R.id.citySettingsBtnBar);
        deleteSettingsBtnBar = findViewById(R.id.deleteSettingsBtnBar);
        changeProfilePicTxt = findViewById(R.id.changeProfilePicTxt);
        profilePic = findViewById(R.id.newProfilePic);
        newProfilePicFeedback = findViewById(R.id.newProfilePicFeedback);
        changeProfilePicBtn = findViewById(R.id.changeProfilePicBtn);
        changeCityTV = findViewById(R.id.changeCityTV);
        newCityACTV = findViewById(R.id.newCityACTV);
        newCityFeedback = findViewById(R.id.newCityFeedback);
        changeCityBtn = findViewById(R.id.changeCityBtn);
        deleteAccountTV = findViewById(R.id.deleteAccountTV);
        deleteAccountInstructionTV = findViewById(R.id.deleteAccountInstructionTV);
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteAccountFeedback = findViewById(R.id.deleteAccountFeedback);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        settingsTV.setTypeface(productSansFont);
        changeProfilePicTxt.setTypeface(productSansFont);
        newProfilePicFeedback.setTypeface(productSansFont);
        changeProfilePicBtn.setTypeface(productSansFont);
        changeCityTV.setTypeface(productSansFont);
        newCityACTV.setTypeface(productSansFont);
        newCityFeedback.setTypeface(productSansFont);
        changeCityBtn.setTypeface(productSansFont);
        deleteAccountTV.setTypeface(productSansFont);
        deleteAccountInstructionTV.setTypeface(productSansFont);
        deleteBtn.setTypeface(productSansFont);
        deleteAccountFeedback.setTypeface(productSansFont);
    }

    private void setRepositories() {
        Intent i = getIntent();
        String username = i.getStringExtra("username"),
                token = i.getStringExtra("token"),
                baseServerDomain = i.getStringExtra("baseServerDomain");
        this.settingsRepository = new SettingsRepository(baseServerDomain, token, username);
        this.citiesRepository = new CitiesRepository();
    }

    private void retrieveUserDetails() {
        settingsRepository.retrieveUserDetails(this, profilePic, newCityACTV);
    }

    private void retrieveCities() {
        citiesRepository.getCitiesInIsrael(this, newCityACTV);
    }

    private void addLocalFunctionalities() {
        setClickListenersForBtnBar();
        setClickListenerForAccountDeletion();
        enableImageUpload();
        handleImageSubmission();
        handleCityInput();
    }


    private void setClickListenersForBtnBar() {
        profilePicSettingsBtnBar.setOnClickListener(view -> genericClickListenerForBtnBar(0));
        citySettingsBtnBar.setOnClickListener(view -> genericClickListenerForBtnBar(1));
        deleteSettingsBtnBar.setOnClickListener(view -> genericClickListenerForBtnBar(2));
    }

    private void genericClickListenerForBtnBar(int indexOfPressedBtn) {
        if (currentClickedBtn == indexOfPressedBtn) {
            return;
        } else {
            disablePrevClick();
            applyNewClick(indexOfPressedBtn);
            currentClickedBtn = indexOfPressedBtn;
        }
    }

    private void disablePrevClick() {
        Button prevBtn = (Button) settingsBtnBar.getChildAt(currentClickedBtn);
        clearPressedBackground(prevBtn);
        // set visibility of corresponding section to gone:
        settingsActivity.getChildAt(currentClickedBtn + 2).setVisibility(View.GONE);
    }

    private void clearPressedBackground(Button prevBtn) {
        Drawable clearBg = ActivityCompat.getDrawable(this, R.drawable.round_button_settings_btn_bar);
        prevBtn.setBackground(clearBg);
    }

    private void applyNewClick(int clickedBtn) {
        Button newBtn = (Button) settingsBtnBar.getChildAt(clickedBtn);
        updateBgOfClickedBtn(newBtn);
        //set visibility of corresponding section to visible:
        settingsActivity.getChildAt(clickedBtn + 2).setVisibility(View.VISIBLE);
    }

    private void updateBgOfClickedBtn(Button newBtn) {
        Drawable pressedBg = ActivityCompat.getDrawable(this, R.drawable.round_button_settings_btn_bar_pressed);
        newBtn.setBackground(pressedBg);
    }

    private void setClickListenerForAccountDeletion() {
        deleteBtn.setOnLongClickListener(v -> {
            settingsRepository.deleteUser(this, deleteAccountFeedback);
            return false;
        });
    }

    private void enableImageUpload() {
        //the ActivityResultLauncher is used to get the result (image chosen) from the activity which is about to be invoked (the image selection screen)
        //its 2nd argument is the callback function to be called after the image selection activity is chosen
        ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::imagePickerCallBack);
        profilePic.setOnClickListener(v -> {
            pickImageLauncher.launch("image/*"); //launch the image selection activity
        });
    }

    private void imagePickerCallBack(android.net.Uri uri) { //callback function after image has been selected
        newProfilePicFeedback.setText("");
        //uri is the reference to the image chosen
        if (uri != null) {
            try {
                //get binary representation of the image chosen, and set it to be the source of the ImageButton:
                profilePicBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profilePic.setImageBitmap(profilePicBitmap);
                enableImageSubmission();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleImageSubmission() {
        //set click listener with desired functionality
        changeProfilePicBtn.setOnClickListener(v -> {
            if (profilePicBitmap != null) {
                profilePicBase64 = getB64Rep();
                settingsRepository.updateUserProfilePic(this, profilePicBase64, newProfilePicFeedback);
            }
        });
    }

    private void enableImageSubmission() {
        //enables button's functionality when new image is chosen
        changeProfilePicBtn.setEnabled(true);
        changeProfilePicBtn.setBackground(ActivityCompat.getDrawable(this, R.drawable.round_button));
    }

    private String getB64Rep() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        profilePicBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void handleCityInput() {
        newCityACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newCityFeedback.setText("");
                String currInput = newCityACTV.getText().toString();
                if (citiesRepository.getCities().contains(currInput)) {
                    enableCitySubmission();
                } else {
                    disableCitySubmission();
                }
            }
        });
        changeCityBtn.setOnClickListener(v -> {
            String newCity = newCityACTV.getText().toString();
            this.settingsRepository.updateUserCity(this, newCity, newCityFeedback);
        });
    }
    private void enableCitySubmission() {
        changeCityBtn.setEnabled(true);
        changeCityBtn.setBackground(ActivityCompat.getDrawable(this, R.drawable.round_button));
    }

    private void disableCitySubmission() {
        changeCityBtn.setEnabled(false);
        changeCityBtn.setBackground(ActivityCompat.getDrawable(this, R.drawable.round_button_disabled));
    }
}