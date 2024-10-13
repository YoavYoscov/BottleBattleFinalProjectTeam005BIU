package com.bottlebattle.bottlebattle.Activities.RecycleItemActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI.RecycleItemsRepository;
import com.bottlebattle.bottlebattle.R;

public class RecycleItemActivity extends AppCompatActivity {
    static final int REQUEST_CODE = 100;
    private final int BTN_BAR_LEN = 6;
    private TextView recycleItemHeadline;
    private TextView nameBarcodeTV;
    private AutoCompleteTextView nameBarcodeACTV;
    private ImageButton barcodeCamera;
    private TextView nameBarcodeQuantityTV;
    private NumberPicker nameBarcodeQuantityNP;
    private Button submitNameBarcodeBtn;
    private TextView submitNameFeedback;
    private TextView orText;
    private TextView categoryTV;
    private LinearLayout categoryButtonBar;
    private Button smallPlasticBottleBtn;
    private Button mediumPlasticBottleBtn;
    private Button largePlasticBottleBtn;
    private Button glassBottleBtn;
    private Button cartonBoxBtn;
    private Button papersBtn;
    int currPressedInBtnBar = -1; //index of currently pressed button
    private TextView categoryQuantityTV;
    private NumberPicker categoryQuantityNP;
    private Button submitCategoryBtn;
    private TextView submitCategoryFeedback;
    private ProgressBar progressBar;
    private RecycleItemsRepository recycleItemsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_item);
        assignVarsToLayouts();
        setFonts();
        setRecycleItemsRepository();
        disableClickOnLoading();
        addFunctionalities();
    }

    private void disableClickOnLoading() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }
    private void assignVarsToLayouts() {
        recycleItemHeadline = findViewById(R.id.recycleItemHeadline);
        nameBarcodeTV = findViewById(R.id.nameBarcodeTV);
        nameBarcodeACTV = findViewById(R.id.nameBarcodeACTV);
        barcodeCamera = findViewById(R.id.barcodeCamera);
        nameBarcodeQuantityTV = findViewById(R.id.nameBarcodeQuantityTV);
        nameBarcodeQuantityNP = findViewById(R.id.nameBarcodeQuantityNP);
        submitNameBarcodeBtn = findViewById(R.id.submitNameBarcodeBtn);
        submitNameFeedback = findViewById(R.id.submitNameFeedback);
        orText = findViewById(R.id.orText);
        categoryTV = findViewById(R.id.categoryTV);
        categoryButtonBar = findViewById(R.id.categoryButtonBar);
        smallPlasticBottleBtn = findViewById(R.id.smallPlasticBottleBtn);
        mediumPlasticBottleBtn = findViewById(R.id.mediumPlasticBottleBtn);
        largePlasticBottleBtn = findViewById(R.id.largePlasticBottleBtn);
        glassBottleBtn = findViewById(R.id.glassBottleBtn);
        cartonBoxBtn = findViewById(R.id.cartonBoxBtn);
        papersBtn = findViewById(R.id.papersBtn);
        categoryQuantityTV = findViewById(R.id.categoryQuantityTV);
        categoryQuantityNP = findViewById(R.id.categoryQuantityNP);
        submitCategoryBtn = findViewById(R.id.submitCategoryBtn);
        submitCategoryFeedback = findViewById(R.id.submitCategoryFeedback);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setFonts() {
        Typeface productSansFont = Typeface.createFromAsset(getAssets(), "fonts/Product Sans Regular.ttf");
        recycleItemHeadline.setTypeface(productSansFont);
        nameBarcodeTV.setTypeface(productSansFont);
        nameBarcodeACTV.setTypeface(productSansFont);
        nameBarcodeQuantityTV.setTypeface(productSansFont);
        submitNameBarcodeBtn.setTypeface(productSansFont);
        submitNameFeedback.setTypeface(productSansFont);
        orText.setTypeface(productSansFont);
        categoryTV.setTypeface(productSansFont);
        smallPlasticBottleBtn.setTypeface(productSansFont);
        mediumPlasticBottleBtn.setTypeface(productSansFont);
        largePlasticBottleBtn.setTypeface(productSansFont);
        glassBottleBtn.setTypeface(productSansFont);
        cartonBoxBtn.setTypeface(productSansFont);
        papersBtn.setTypeface(productSansFont);
        categoryQuantityTV.setTypeface(productSansFont);
        submitCategoryBtn.setTypeface(productSansFont);
        submitCategoryFeedback.setTypeface(productSansFont);
    }

    private void setRecycleItemsRepository() {
        Intent i = getIntent();
        String username = i.getStringExtra("username");
        String token = i.getStringExtra("token");
        String baseServerDomain = i.getStringExtra("baseServerDomain");
        recycleItemsRepository = new RecycleItemsRepository(baseServerDomain, token, username);
    }

    private void addFunctionalities() {
        setProductsDropDownList();
        setNumPickers();
        enableNameBarcodeSubmission();
        setClickListenersForBtnBar();
        enableCategorySubmission();
    }

    private void setProductsDropDownList() {
        recycleItemsRepository.setProductsDropDownList(this, nameBarcodeACTV);
    }

    private void setNumPickers() {
        nameBarcodeQuantityNP.setMinValue(1);
        nameBarcodeQuantityNP.setMaxValue(20);
        categoryQuantityNP.setMinValue(1);
        categoryQuantityNP.setMaxValue(20);
    }

    private void setClickListenersForBtnBar() {
        for (int i = 0; i < BTN_BAR_LEN; i++) {
            Button btn = (Button) categoryButtonBar.getChildAt(i);
            int index = i; //copying i to an (effectively) final variable to be accessed in lambda expression
            btn.setOnClickListener(v -> clickListenerForBtnBar(btn, index));
        }
    }

    private void clickListenerForBtnBar(Button pressedBtn, int indexOfPressedButton) {
        if (currPressedInBtnBar == -1) {
            //first button pressed in btn bar -> enable category submission
            submitCategoryBtn.setEnabled(true);
            submitCategoryBtn.setBackground(ActivityCompat.getDrawable(this, R.drawable.round_button));
        } else {
            // pressedBtn isn't the first button pressed, so we need to un-press prev button:
            Drawable unPressedBtnBg = ActivityCompat.getDrawable(this, R.drawable.round_button3_middle);
            categoryButtonBar.getChildAt(currPressedInBtnBar).setBackground(unPressedBtnBg);
        }
        // click curr button:
        Drawable pressedBtnBg = ActivityCompat.getDrawable(this, R.drawable.round_button3_middle_pressed);
        pressedBtn.setBackground(pressedBtnBg);
        currPressedInBtnBar = indexOfPressedButton;
    }
    private void enableCategorySubmission() {
        submitCategoryBtn.setOnClickListener(v -> {
            //on submission, show loading progress bar:
            showLoadingProgressBar();
            int quantity = categoryQuantityNP.getValue();
            recycleItemsRepository.submitCategory(this, currPressedInBtnBar, quantity, submitCategoryFeedback);
        });
    }
    private void showLoadingProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        disableClickOnLoading();
    }
    private void enableNameBarcodeSubmission() {
        enableSubmissionViaText();
        enableSubmissionViaBarcodePic();
        setClickListenersForNameBarcodeBtn();
    }

    private void enableSubmissionViaText() {
        nameBarcodeACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currInput = nameBarcodeACTV.getText().toString();
                enableNameBarcodeSubmissionButton(currInput);
            }
        });
    }

    private void enableSubmissionViaBarcodePic() {
        barcodeCamera.setOnClickListener(v -> {
            Intent intent = new Intent(RecycleItemActivity.this, ScanningBarcodeActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private void enableNameBarcodeSubmissionButton(String nameOrBarcode) {
        if (recycleItemsRepository.getAllNamesAndBarcodes().contains(nameOrBarcode)) {
            submitNameBarcodeBtn.setEnabled(true);
            submitNameBarcodeBtn.setBackground(ActivityCompat.getDrawable(this, R.drawable.round_button));
        } else {
            submitNameBarcodeBtn.setEnabled(false);
            submitNameBarcodeBtn.setBackground(ActivityCompat.getDrawable(this, R.drawable.round_button_disabled));
        }
    }

    private void setClickListenersForNameBarcodeBtn() {
        submitNameBarcodeBtn.setOnClickListener(v -> {
            showLoadingProgressBar();
            String unformattedNameOrBarcode = nameBarcodeACTV.getText().toString();
            int quantity = nameBarcodeQuantityNP.getValue(); //if via name or category, use default quantity (i.e. 1).
            recycleItemsRepository.submitNameOrBarcode(this, unformattedNameOrBarcode, quantity, submitNameFeedback);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    // Get the data from the result Intent
                    String result = data.getStringExtra("key");
                    // Added now:
                    nameBarcodeACTV.setText(result);
                    enableNameBarcodeSubmissionButton(result);
                }
            } //else {
                // If we want to handle other result codes in the future for some reason... }
        }
    }
}