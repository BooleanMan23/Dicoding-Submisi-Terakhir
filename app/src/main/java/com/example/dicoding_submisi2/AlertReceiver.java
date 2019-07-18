package com.example.dicoding_submisi2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    String messege;

    @Override
    public void onReceive(Context context, Intent intent) {
        messege = intent.getStringExtra("pesan") ;
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(messege);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
