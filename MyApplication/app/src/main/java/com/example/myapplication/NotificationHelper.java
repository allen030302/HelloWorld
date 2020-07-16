package com.example.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mManager;


    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String message) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new Intent();
        Bundle bag=new Bundle();
        bag.putFloat("BMI_EXTRA",0);
        bag.putString("Page","1");
        bag.putInt("Notification",1);
        intent.putExtras(bag);
        intent.setClass(this , OrderPage.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 1,intent, PendingIntent.FLAG_CANCEL_CURRENT);


        return  mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Alarm!")
                .setContentText(message)
                .setSmallIcon(R.drawable.android)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.android, "BMI", pendingIntent2)
                .addAction(R.drawable.calculator,"home",pendingIntent)
                .setAutoCancel(true);
    }

}
