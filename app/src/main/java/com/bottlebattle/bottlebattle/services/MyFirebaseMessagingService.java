package com.bottlebattle.bottlebattle.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bottlebattle.bottlebattle.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.widget.Toast;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        //Log.d("TAG", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

        // sendRegistrationToServer(token);
        // Note: in our app, this is dealt with in another place.
    }


    private void showToast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyFirebaseMessagingService.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Saving the title & body of the message received from the server:
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        // We can use the title & body to determine the type of notification received:
        // In the future we can use this to update information in the app.
        if (title != null) {
            if (title.toLowerCase().contains("leaderboard") || body.toLowerCase().contains("leaderboard")) {
                //showToast("Identified a leaderboard change notification!");
            } else if (title.toLowerCase().contains("logged") || body.toLowerCase().contains("logged")) {
                //showToast("Identified a login notification!");
            }

        }


        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("MyFirebaseMessagingService", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d("MyFirebaseMessagingService", "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//
//        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            // Defining channel ID (could be changed later on):
            String channelId = "fcm_default_channel";
            // Building notification, with notification title received from the server & notification body (content) received from the server:
            // Also defining the notification icon to appear in the notification bar.
            // Finally, we set the priority of the notification to PRIORITY_DEFAULT.
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_bottle_battle_no_background_foreground)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Note: only Android Oreo and above requires a notification channel:
            // Hence, we check if the device is running Android Oreo or above, and only then create a notification channel.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(channelId,"Messages", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Messages from BottleBattle will be received through this channel");
                notificationManager.createNotificationChannel(channel);
            }
            // Finally, we use the notification manager to present the user with the notification we have defined above:
            notificationManager.notify(1, notificationBuilder.build());
        }


    }
}
