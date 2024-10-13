package com.bottlebattle.bottlebattle.APIs.RecycleItemsAPI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.bottlebattle.bottlebattle.Activities.ItemRecycledActivity;
import com.bottlebattle.bottlebattle.R;

public class AddRecycleTransactionCallbackHandler {
    private final Context context;
    private final RecycleTransactionReqDTO recycleTransactionReq;
    private final RecycleItemDTO recycleItem;
    private final TextView feedback;

    public AddRecycleTransactionCallbackHandler(Context context, RecycleTransactionReqDTO recycleTransactionReq,
                                                RecycleItemDTO recycleItem, TextView feedback) {
        this.context = context;
        this.recycleTransactionReq = recycleTransactionReq;
        this.recycleItem = recycleItem;
        this.feedback = feedback;
    }

    public void onSuccess(String baseServerDomain, String token) {
        Intent i = new Intent(context, ItemRecycledActivity.class);
        i.putExtra("recycleItemId", recycleTransactionReq.getRecycleItemId());
        i.putExtra("suitableRecycleBin", recycleItem.getSuitableRecycleBin());
        i.putExtra("baseServerDomain", baseServerDomain);
        i.putExtra("token", token);
        context.startActivity(i);
        ((Activity) context).finish();
    }

    public void onError() {
        feedback.setText(context.getResources().getString(R.string.an_error_occurred_please_try_again_later));
    }
}
