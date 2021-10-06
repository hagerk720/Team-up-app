package com.example.teamup.SendNotificationPack;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.teamup.R;
import com.example.teamup.activities.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("onMessageReceived", "From: " + remoteMessage.getFrom());

        String  sent = remoteMessage.getData().get("sent");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null ){
            /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                SendOreoAndAboveNotification(remoteMessage);
            }
            else {
                SendNormalNotification(remoteMessage);
            }*/
            SendNormalNotification(remoteMessage);
        }
    }

    private void SendNormalNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        Log.d("userid" ,user);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]",""));

        NotificationDatabase(user , title);
        Bundle bundle = new Bundle();
        bundle.putString("userId" , user);
        PendingIntent pIntent = new NavDeepLinkBuilder(this)
                .setGraph(R.navigation.home_activity_navigation_graph)
                .setDestination(R.id.navigation_menu_myChat)
                .setComponentName(HomeActivity.class)
                .setArguments(bundle)
                .createPendingIntent();

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentText(body)
                .setSound(sound)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setSmallIcon(Integer.parseInt(icon));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int i = 0 ;
        if (j > 0 ){
            i = j ;
        }
        notificationManager.notify(i , builder.build());

    }

    private void SendOreoAndAboveNotification(RemoteMessage remoteMessage) {
    }
    public void NotificationDatabase(String userId , String msgBody){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sentID").setValue(userId);
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("msg").setValue(msgBody);
    }
}
