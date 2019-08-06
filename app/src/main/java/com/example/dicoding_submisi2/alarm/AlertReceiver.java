package com.example.dicoding_submisi2.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int i = intent.getIntExtra("nomor", 0);
        String judul = intent.getStringExtra("namaMovie");
        Log.i("onReceive", String.valueOf(i));
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(judul, i);
        notificationHelper.getManager().notify(i, nb.build());
    }
}