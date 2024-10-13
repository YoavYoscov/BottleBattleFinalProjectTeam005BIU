package com.bottlebattle.bottlebattle.APIs.UserAPI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bottlebattle.bottlebattle.R;

import java.net.URI;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePicUpdater {
    //given a base-64 OR a url string representing a profile pic, and an ImageView, display the desired pic in the IV

    public ProfilePicUpdater() {
        //default ctor
    }

    public void updateProfilePic(Context context, String profilePicRes, CircleImageView imageView) {
        if (isUri(profilePicRes)) { //if it is a google user, profilePic response is a Uri
            Glide.with(context).load(profilePicRes).into(imageView);
        } else if (profilePicRes == null || profilePicRes.isEmpty() || profilePicRes.equals("none")) { //if it is a default-registered user that hasn't chosen a picture, use default generic profile pic
            imageView.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.default_pfp));
        } else { //else, if a default-registered user has chosen a picture, profilePicRes is its base64
                try {
                    byte[] decodedString = Base64.decode(profilePicRes, Base64.DEFAULT);
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    if (decodedBitmap != null) {
                        imageView.setImageBitmap(decodedBitmap);
                    } else {
                        //if decode failed, use default generic picture
                        updateProfilePic(context, "none", imageView);
                    }
                } catch (Exception e) {
                    //if some exception occurred, use default generic picture
                    updateProfilePic(context, "none", imageView);
            }

        }
    }

    private boolean isUri(String str) {
        try {
            URI uri = new URI(str);
            return uri.isAbsolute() && uri.getScheme() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
