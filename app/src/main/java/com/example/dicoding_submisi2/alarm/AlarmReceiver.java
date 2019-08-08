package com.example.dicoding_submisi2.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.dicoding_submisi2.Movie;
import com.example.dicoding_submisi2.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmReceiver extends BroadcastReceiver {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static  String CATEGORY = "movie";
    public static  String API_KEY = "b9239506432ea4c54f8b16f4a3679008";
    public static  String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public static final String TYPE_UPCOMING = "UpcomingAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private String TIME_FORMAT = "HH:mm";
    // Siapkan 2 id untuk 2 macam alarm, onetime dna repeating
    private final int ID_UPCOMING = 100;
    private final int ID_REPEATING = 103;



    public AlarmReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        if (type.equals(TYPE_UPCOMING)){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = sdf.format(new Date());
            final ArrayList<Movie> movieList = new ArrayList<>();


            Log.i("date now", currentDateandTime);
            String PRIMARY_RELEASE_DATE_GTE = currentDateandTime;
            String PRIMARY_RELEASE_DATE_ITE = currentDateandTime;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ReleaseTodayInterface apiInterface = retrofit.create(ReleaseTodayInterface.class);
            Call<ReleaseTodayMovie> call = apiInterface.listOfTodayMovie(CATEGORY,API_KEY, PRIMARY_RELEASE_DATE_GTE, PRIMARY_RELEASE_DATE_ITE);
            call.enqueue(new Callback<ReleaseTodayMovie>() {
                @Override
                public void onResponse(Call<ReleaseTodayMovie> call, Response<ReleaseTodayMovie> response) {
                    ReleaseTodayMovie results = response.body();
                    List<ReleaseTodayMovie.ResultsBean> listOfMovies = results.getResults();

                    for (ReleaseTodayMovie.ResultsBean resultsBean : listOfMovies){
                        double id = resultsBean.getId();
                        String title = resultsBean.getTitle();
                        String overview = resultsBean.getOverview();
                        String releaseDate = resultsBean.getRelease_date();
                        String posterLink = POSTER_BASE_URL+resultsBean.getPoster_path();
                        Movie movie = new Movie(id,title, overview, posterLink, releaseDate, true);
                        Log.i("data movie id", String.valueOf(movie.getId()));
                        Log.i("data movie judul", movie.getTitle());
                        Log.i("data movie overview", movie.getOverview());
                        Log.i("data movie releaseDate", movie.getReleaseDate());
                        Log.i("data movie posterLink", movie.getPosterLink());
                        movieList.add(movie);
                        String pesan = title + " Release Now";
                        showAlarmNotification(context, title, pesan , (int) id);
                        Log.i("size", String.valueOf(movieList.size()));
                    }





                }

                @Override
                public void onFailure(Call<ReleaseTodayMovie> call, Throwable t) {
                    Log.i("onFailure", "true");
                    t.printStackTrace();
                }
            });






        }
        else {
            String title = TYPE_REPEATING;
            showAlarmNotification(context, title, message, ID_REPEATING);
            showToast(context, title, message);
        }


    }

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }


    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent;
        if (type.equals(TYPE_REPEATING)){
             pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        }
        else {
             pendingIntent = PendingIntent.getBroadcast(context, ID_UPCOMING, intent, 0);
        }


        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }


        if (type.equals(TYPE_REPEATING)){
            Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
        }
        if (type.equals(TYPE_UPCOMING)){
            Toast.makeText(context, "Upcoming alarm set up", Toast.LENGTH_SHORT).show();
        }


    }

    public void cancelAlarm(Context context, String type) {
        Log.i("cancelAlarm", "cancelAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode;
        if (type.equals(TYPE_UPCOMING)){
            requestCode = ID_UPCOMING;
        }
        else {
            requestCode = ID_REPEATING;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        if (type.equals(TYPE_UPCOMING)){
            Toast.makeText(context, "Upcoming alarm dibatalkan", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show();
        }

    }



}
