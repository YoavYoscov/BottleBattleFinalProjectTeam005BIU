package com.bottlebattle.bottlebattle.Activities.UserRankingsActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.ProfilePicUpdater;
import com.bottlebattle.bottlebattle.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {
    private List<UserDetailsDTO> usersList;
    private final LayoutInflater inflater;
    private final Context context;
    private final String loggedInUser;

    public TableAdapter(Context context, List<UserDetailsDTO> data, String loggedInUser) {
        this.usersList = data;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.loggedInUser = loggedInUser;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.table_row, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        UserDetailsDTO userDetails = usersList.get(position);
        //display profile pic:
        String profilePicRes = userDetails.getProfilePic();
        ProfilePicUpdater profilePicUpdater = new ProfilePicUpdater();
        profilePicUpdater.updateProfilePic(context, profilePicRes, holder.tableProfilePic);
        //display username:
        holder.tableUsername.setText(userDetails.getUsername());
        //display points:
        int points = (int) userDetails.getUserPoints(); //convert points received from server to int before display
        holder.tableUserPoints.setText(Integer.toString(points));

        //also, if the bound view is the row of logged in user, change its background to green:
        if (userDetails.getUsername() != null && userDetails.getUsername().trim().equals(loggedInUser.trim())) {
            holder.tableRow.setBackgroundColor(ActivityCompat.getColor(context, R.color.menu_item_bg_recycle));
        } else {
            holder.tableRow.setBackgroundColor(ActivityCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void updateTable(ArrayList<UserDetailsDTO> newUsersList) {
        this.usersList = newUsersList;
        notifyDataSetChanged();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        final TableRow tableRow;
        final CircleImageView tableProfilePic;
        final TextView tableUsername;
        final TextView tableUserPoints;

        TableViewHolder(View itemView) {
            super(itemView);
            tableRow = itemView.findViewById(R.id.tableRow);
            tableProfilePic = itemView.findViewById(R.id.tableProfilePic);
            tableUsername = itemView.findViewById(R.id.tableUsername);
            tableUserPoints = itemView.findViewById(R.id.tableUserPoints);
        }
    }
}
