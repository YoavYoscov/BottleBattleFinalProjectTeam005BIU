package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.bottlebattle.bottlebattle.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsListCallbackHandler {
    private final Context context;
    private final AutoCompleteTextView nameBarcodeACTV;
    private final ArrayList<RecycleItemDTO> allRecycleItems;
    private final ArrayList<String> allNamesAndBarcodes;
    private final ArrayList<String> categories;

    public ProductsListCallbackHandler(Context context, AutoCompleteTextView nameBarcodeACTV,
                                       ArrayList<RecycleItemDTO> allRecycleItems,
                                       ArrayList<String> allNamesAndBarcodes,
                                       ArrayList<String>categories) {
        this.context = context;
        this.nameBarcodeACTV = nameBarcodeACTV;
        this.allRecycleItems = allRecycleItems;
        this.allNamesAndBarcodes = allNamesAndBarcodes;
        this.categories = categories;
    }

    public void updateListsAndProductsDropDownMenu(List<RecycleItemDTO> allItems) {
        allRecycleItems.addAll(allItems); //allItems is the list retrieved from server, and allRecycleItems is the list stored in repository
        for (RecycleItemDTO recycleItem : allRecycleItems) {
            String prodName = recycleItem.getProductName(), manufacturer = recycleItem.getManufacturer();
            if (!isCategory(prodName, categories)) { //categories aren't added to the drop down menu
                //save each products name in the string list
                allNamesAndBarcodes.add(prodName + " (" + manufacturer + ")");
                //for each product, save its each of its barcodes in the string list
                allNamesAndBarcodes.addAll(recycleItem.getBarcode());
            }
        }
        //set the adapter for the products drop-down menu:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.product_name_layout, allNamesAndBarcodes);
        nameBarcodeACTV.setAdapter(adapter);
        nameBarcodeACTV.setThreshold(1); //starts suggesting from 1st character
        //remove progress bar:
        ProgressBar progressBar = ((Activity) context).findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        //enable screen touch-ability after loading:
        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private boolean isCategory(String name, ArrayList<String> categories) {
        for (String category : categories) {
            if (category.equals(name)) return true;
        }
        return false;
    }

}
