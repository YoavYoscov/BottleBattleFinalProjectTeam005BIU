package com.bottlebattle.bottlebattle.Activities.HomeScreenActivity.Menu;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bottlebattle.bottlebattle.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    //a view adapter for the ViewPager used for displaying the carousel menu:
    private final int[] menuItems = {R.drawable.menu_item_bg_recycle,R.drawable.menu_item_bg_ranks,
            R.drawable.menu_item_bg_progress, R.drawable.menu_item_bg_settings, R.drawable.menu_item_bg_friends,
            R.drawable.menu_item_bg_stats, R.drawable.menu_item_privacy_policy}; //the design (background) for each menu item
    private final int[] texts = {R.string.add_a_recycled_item, R.string.user_rankings, R.string.see_your_progress,
    R.string.settings, R.string.add_friends, R.string.see_your_stats, R.string.privacy_policy}; //the text for each menu item
    private final int[] textColors = {R.color.menu_item_recycle_text, R.color.menu_item_ranks_text,
            R.color.menu_item_progress_text, R.color.menu_item_settings_text, R.color.menu_item_add_friends_text,
            R.color.menu_item_stats_text, R.color.menu_item_privacy_policy_text}; //the color for the text of each menu item
    private final Context context; //the context is passed as an argument in the ctor as it is necessary for generating the LayoutInflater
    private final MenuClickHandler menuClickHandler;
    private final Typeface productSansFontBold;


    public MenuAdapter(Context context, String username, String token, String baseServerDomain) {
        this.context = context;
        menuClickHandler  = new MenuClickHandler(context, username, token, baseServerDomain);
        productSansFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/Product Sans Bold.ttf");
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creating the ViewHolder obj (the ViewHolder class is defined below as an inner class)
        //it is an object with one attribute - the menuItem to be displayed
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false); //create the menuItem view using the LayoutInflater
        return new ViewHolder(view); //create the ViewHolder obj with the created menuItem view
    }

    @Override
    public int getItemCount() {
        // Return a large value to enable infinite scrolling
        return Integer.MAX_VALUE;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        //given the created view (held by the ViewHolder), set its background, text, text-color, font and clickHandler:
        //we make the adapter think there are many (Integer.MAX_VALUE) items in the menu
        //thus, when the adapter think it should display the item in position x, we actually want the item in position (x % menu length)
        int actualPosition = position % menuItems.length;
        holder.menuItem.setBackgroundResource(menuItems[actualPosition]);
        holder.menuItem.setText(texts[actualPosition]);
        holder.menuItem.setTextColor(ContextCompat.getColor(context, textColors[actualPosition]));
        holder.menuItem.setTypeface(productSansFontBold);
        //for the click handler, we use the MenuClickHandler obj created in ctor:
        holder.menuItem.setOnClickListener(v -> menuClickHandler.handleClick(actualPosition));
       }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button menuItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuItem = itemView.findViewById(R.id.menuItem);
        }
    }
}
