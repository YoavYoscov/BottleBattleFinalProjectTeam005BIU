package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bottlebattle.bottlebattle.APIs.UserAPI.ProfilePicUpdater;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;
import com.bottlebattle.bottlebattle.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListAdapter extends ArrayAdapter<UserDetailsDTO> {
    private final Context context;

    public UsersListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserDetailsDTO> usersList) {
        super(context, resource, usersList);
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.user_element_in_list, parent, false);
        }
        UserDetailsDTO user = getItem(position);
        //update username:
        TextView textView = convertView.findViewById(R.id.userElementUsername);
        textView.setText(user.toString());
        //update profilePic:
        ProfilePicUpdater profilePicUpdater = new ProfilePicUpdater();
        CircleImageView imageView = convertView.findViewById(R.id.userElementProfilePic);
        String profilePicRes = user.getProfilePic();
        profilePicUpdater.updateProfilePic(context, profilePicRes, imageView);
        return convertView;
    }
}
