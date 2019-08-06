package com.example.dicoding_submisi2.alarm;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.dicoding_submisi2.R;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

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

    public NotificationCompat.Builder getChannelNotification(String judulMovie, int value) {

        if (value == 0){
            Log.i("daily notification", "alarm is on");
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle("Hello There")
                    .setContentText("Daily Notification")
                    .setSmallIcon(R.drawable.notification);
        }
        else{
            Log.i("alarm", "alarm is on");
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle(judulMovie)
                    .setContentText("Release Now!")
                    .setSmallIcon(R.drawable.notification);
        }

    }
}