package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;
import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class RecycleItemsRepository {
    private final RecycleItemsAPI recycleItemsAPI;
    private final String username;
    private final ArrayList<RecycleItemDTO> allRecycleItems = new ArrayList<>();
    private final ArrayList<String> allNamesAndBarcodes = new ArrayList<>();
    private final ArrayList<String> categories = new ArrayList<>(
            Arrays.asList("Small Plastic Bottle", "Medium Plastic Bottle", "Large Plastic Bottle", "Glass Bottle", "Carton Box", "Paper"));

    public RecycleItemsRepository(String baseServerAddress, String token, String username) {
        this.recycleItemsAPI = new RecycleItemsAPI(baseServerAddress, token);
        this.username = username;
    }

    public ArrayList<String> getAllNamesAndBarcodes() {
        return allNamesAndBarcodes;
    }

    public void setProductsDropDownList(Context context, AutoCompleteTextView nameBarcodeACTV) {
        ProductsListCallbackHandler productsListCallbackHandler =
                new ProductsListCallbackHandler(context, nameBarcodeACTV, allRecycleItems, allNamesAndBarcodes, categories);
        recycleItemsAPI.getAllRecycleItems(productsListCallbackHandler);
    }

    private RecycleItemDTO getRecycleItemFromNameBarcodeOrCategory(String nameOrBarcode) {
        RecycleItemDTO result = allRecycleItems.get(0); //initialization (for safety)
        String formattedName = nameOrBarcode.trim(); //trim to remove redundant spaces and avoid wrong results in strings comparison
        for (RecycleItemDTO recycleItem : allRecycleItems) {
            if (recycleItem.getProductName().trim().equals(formattedName)) {
                result = recycleItem;
                break;
            }
            if (recycleItem.getBarcode().contains(nameOrBarcode)) {
                result = recycleItem;
                break;
            }
        }
        return result;
    }

    public void submitCategory(Context context, int indexOfPressedCategoryBtn, int quantity, TextView submitCategoryFeedback) {
        String categoryName = categories.get(indexOfPressedCategoryBtn);
        RecycleItemDTO recycleItem =  getRecycleItemFromNameBarcodeOrCategory(categoryName);
        RecycleTransactionReqDTO recycleTransactionReq = new RecycleTransactionReqDTO(username, recycleItem.get_id(), quantity);
        AddRecycleTransactionCallbackHandler callbackHandler =
                new AddRecycleTransactionCallbackHandler(context, recycleTransactionReq, recycleItem, submitCategoryFeedback);
        recycleItemsAPI.addRecycleTransaction(callbackHandler, recycleTransactionReq);
    }

    public void submitNameOrBarcode(Context context, String unformattedNameOrBarcode, int quantity, TextView submitNameFeedback) {
        String formattedNameOrBarcode = getProductNameFromString(unformattedNameOrBarcode);
        RecycleItemDTO recycleItem = getRecycleItemFromNameBarcodeOrCategory(formattedNameOrBarcode);
        RecycleTransactionReqDTO recycleTransactionReq = new RecycleTransactionReqDTO(username, recycleItem.get_id(), quantity);
        AddRecycleTransactionCallbackHandler callbackHandler =
                new AddRecycleTransactionCallbackHandler(context, recycleTransactionReq, recycleItem, submitNameFeedback);
        recycleItemsAPI.addRecycleTransaction(callbackHandler, recycleTransactionReq);
    }

    private String getProductNameFromString(String str) {
        if (str.matches(".+\\s+\\(.+\\)")) { //if not, it's a barcode (already without manufacturer)
            // Find the index of the first '('
            int startIndex = str.lastIndexOf('(');
            // Extract the product name, trim any leading/trailing spaces
            return str.substring(0, startIndex).trim();
        }
        // If it doesn't match the format, return the input as it is (assuming it's a barcode)
        return str;
    }

    public void getNotesAndSuggestions(String recycleItemId, TextView notesTV, ScrollView notesSV, ProgressBar itemRecycledProgressBar) {
        NotesAndSuggestionsCallbackHandler callbackHandler =
                new NotesAndSuggestionsCallbackHandler(notesSV, notesTV, itemRecycledProgressBar);
        recycleItemsAPI.getNotesAndSuggestions(recycleItemId, callbackHandler);
    }
}
