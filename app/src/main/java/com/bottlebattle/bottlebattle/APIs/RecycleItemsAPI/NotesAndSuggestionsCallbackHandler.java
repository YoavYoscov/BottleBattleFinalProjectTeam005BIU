package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.R;
public class NotesAndSuggestionsCallbackHandler {
    private final ScrollView notesSV;
    private final TextView notesTV;
    private final ProgressBar itemRecycledProgressBar;

    public NotesAndSuggestionsCallbackHandler(ScrollView notesSV, TextView notesTV, ProgressBar itemRecycledProgressBar) {
        this.notesSV = notesSV;
        this.notesTV = notesTV;
        this.itemRecycledProgressBar = itemRecycledProgressBar;
    }

    public void onSuccess(String notesAndSuggestions) {
        if (notesAndSuggestions.isEmpty() || notesAndSuggestions.trim().equals(("No notes and suggestions found").trim())) {
            notesSV.setVisibility(View.GONE); //no notes -> hide notes scroll view
        } else {
            //if there are notes:
            notesTV.setText(notesAndSuggestions); //set notes text view text accordingly
            notesSV.setVisibility(View.VISIBLE); //eventually, display notes scroll view
        }
        //remove progress bar after retrieving notes and suggestions:
        itemRecycledProgressBar.setVisibility(View.GONE);
    }

    public void onError() {
        notesTV.setText(R.string.an_error_occurred_please_try_again_later);
        itemRecycledProgressBar.setVisibility(View.GONE);
    }
}
