package com.example.dicoding_submisi2.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dicoding_submisi2.Movie;
import com.example.dicoding_submisi2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmSettingActivity extends AppCompatActivity {


    Switch upcomingSwitch, dailySwitch;
    SharedPreferences sharedPreferences;
    Context context = this;
    Activity act = AlarmSettingActivity.this;


    public static String BASE_URL = "https://api.themoviedb.org";
    public static  String CATEGORY = "movie";
    public static  String API_KEY = "b9239506432ea4c54f8b16f4a3679008";
    public static  String LANGUAGE = "en-US";
    public static  String SORT_BY = "release_date.asc";
    public static String INCLUDE_ADULT = "false";
    public static String INCLUDE_VIDEO = "false";
    public static  int PAGE = 1;
    public  String PRIMARY_RELEASE_DATE_GTE ;
    public  String PRIMARY_RELEASE_DATE_ITE;
    public static  String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";
    public static ArrayList<Movie> movieList = new ArrayList<>();
    AlarmManager mgrAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);
        upcomingSwitch = (Switch) act.findViewById(R.id.upcomingSwitch);
        dailySwitch    = (Switch) act.findViewById(R.id.dailySwtich);
        mgrAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        sharedPreferences = this.getSharedPreferences("com.example.shared_preferences", Context.MODE_PRIVATE);
        switchState();
        upcomingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    sharedPreferences.edit().putString("upcoming", "On").apply();
                    String upcomingState =sharedPreferences.getString("upcoming","");
                    Toast.makeText(getBaseContext(), upcomingState, Toast.LENGTH_SHORT).show();
                    retrofit();

                }
                else{
                    sharedPreferences.edit().putString("upcoming", "Off").apply();
                    String upcomingState =sharedPreferences.getString("upcoming","");
                    Toast.makeText(getBaseContext(), upcomingState, Toast.LENGTH_SHORT).show();
                    cancelRetrofit();

                }

            }
        });

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    sharedPreferences.edit().putString("daily", "On").apply();
                    String dailyState =sharedPreferences.getString("daily","");
                    Toast.makeText(getBaseContext(), dailyState, Toast.LENGTH_SHORT).show();
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, 7);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    startAlarm(c, 0);

                }
                else{
                    sharedPreferences.edit().putString("daily", "Off").apply();
                    String dailyState =sharedPreferences.getString("daily","");
                    Toast.makeText(getBaseContext(), dailyState, Toast.LENGTH_SHORT).show();
                    cancelAlarm(0);

                }
            }
        });

    }


    public void retrofit(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        Log.i("date now", currentDateandTime);
        PRIMARY_RELEASE_DATE_GTE = currentDateandTime;
        PRIMARY_RELEASE_DATE_ITE = currentDateandTime;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ReleaseTodayMovieInterface apiInterface = retrofit.create(ReleaseTodayMovieInterface.class);
        Call<ReleaseTodayMovie> call = apiInterface.listOfTodayMovie(CATEGORY,API_KEY, LANGUAGE, SORT_BY, INCLUDE_ADULT, INCLUDE_VIDEO, PAGE, PRIMARY_RELEASE_DATE_GTE, PRIMARY_RELEASE_DATE_ITE);
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
                    Log.i("size", String.valueOf(movieList.size()));
                }

                alarm();



            }

            @Override
            public void onFailure(Call<ReleaseTodayMovie> call, Throwable t) {
                Log.i("onFailure", "true");
                t.printStackTrace();
            }
        });




    }

    public void alarm(){
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        Log.i("jumlah movie", String.valueOf(movieList.size()));
        for(int i = 0; i < movieList.size(); ++i)
        {
            Intent intent = new Intent(AlarmSettingActivity.this, AlertReceiver.class);
            intent.putExtra("nomor", i);
            intent.putExtra("namaMovie", movieList.get(i).getTitle());
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmSettingActivity.this, i+1, intent, 0);
            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 8);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            mgrAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            intentArray.add(pendingIntent);
        }
    }

    public void switchState(){
        String dailyState = sharedPreferences.getString("daily", "");
        Log.i("dailyState", dailyState);
        if (dailyState.equals("On")){
            dailySwitch.setChecked(true);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 7);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            startAlarm(c, 0);
        }
        else{
            dailySwitch.setChecked(false);
            cancelAlarm(0);

        }
        String upcomingState = sharedPreferences.getString("upcoming", "");
        Log.i("upcomingState", upcomingState);
        if (upcomingState.equals("On")){
            upcomingSwitch.setChecked(true);
            retrofit();


        }
        else{
            upcomingSwitch.setChecked(false);
            cancelRetrofit();

        }

    }

    public void cancelRetrofit(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        Log.i("date now", currentDateandTime);
        PRIMARY_RELEASE_DATE_GTE = currentDateandTime;
        PRIMARY_RELEASE_DATE_ITE = currentDateandTime;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ReleaseTodayMovieInterface apiInterface = retrofit.create(ReleaseTodayMovieInterface.class);
        Call<ReleaseTodayMovie> call = apiInterface.listOfTodayMovie(CATEGORY,API_KEY, LANGUAGE, SORT_BY, INCLUDE_ADULT, INCLUDE_VIDEO, PAGE, PRIMARY_RELEASE_DATE_GTE, PRIMARY_RELEASE_DATE_ITE);
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
                    Log.i("size", String.valueOf(movieList.size()));
                }

                cancelRetrofitAlarm();





            }

            @Override
            public void onFailure(Call<ReleaseTodayMovie> call, Throwable t) {
                Log.i("onFailure", "true");
                t.printStackTrace();
            }
        });



    }

    public void cancelRetrofitAlarm(){
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        Log.i("jumlah movie", String.valueOf(movieList.size()));
        for(int i = 0; i < movieList.size(); ++i)
        {
            Intent intent = new Intent(AlarmSettingActivity.this, AlertReceiver.class);
            intent.putExtra("nomor", i);
            intent.putExtra("namaMovie", movieList.get(i).getTitle());
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmSettingActivity.this, i, intent, 0);
            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            mgrAlarm.cancel(pendingIntent);
        }
    }

    private void startAlarm(Calendar c, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);


        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void cancelAlarm(int requestCode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);

        alarmManager.cancel(pendingIntent);

    }

}
