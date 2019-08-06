package com.example.dicoding_submisi2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dicoding_submisi2.alarm.AlarmSettingActivity;
import com.example.dicoding_submisi2.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    public static String FRAGMENT_APA = "";
    public static SQLiteDatabase myDatabase;
    public static ArrayList<Double> favoriteId = new ArrayList<Double>();
    ArrayList<Movie> movieList = new ArrayList<>();
    ArrayList<Movie> serialTvList = new ArrayList<>();
    public static  SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.dicoding_submisi2", Context.MODE_PRIVATE);

        try {
            myDatabase = this.openOrCreateDatabase("favorite", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS favorite (title VARCHAR, overview VARCHAR, posterLink VARCHAR, releaseDate VARCHAR,  id DOUBLE, isMovie DOUBLE )");
//            String sql = "DELETE FROM movie WHERE id=0";
//            myDatabase.compileStatement(sql);
        } catch (Exception E) {

        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar); //Inisialisasi dan Implementasi id Toolbar

        setSupportActionBar(toolbar); // Memasang Toolbar pada Aplikasi

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                movieList.clear();
                serialTvList.clear();
                if (FRAGMENT_APA.equals("mv")) {
                    searchMovie(s);

                }
                if (FRAGMENT_APA.equals("st")) {
                    searchTv(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        if(item.getItemId() == R.id.alarm){
            Intent intent = new Intent(MainActivity.this, AlarmSettingActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.testing){
            Intent intent = new Intent(MainActivity.this, testingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public static void showInLog() {
        Cursor c = MainActivity.myDatabase.rawQuery("SELECT * FROM favorite", null);
        //mengambil index kolum
        int titleIndex = c.getColumnIndex("title");
        int overviewIndex = c.getColumnIndex("overview");
        int posterLinkIndex = c.getColumnIndex("posterLink");
        int releaseDateIndex = c.getColumnIndex("releaseDate");
        int idIndex = c.getColumnIndex("id");
        int isMovieIndex = c.getColumnIndex("isMovie");
        Log.i("hasil", "===============");
        c.moveToFirst();
        while (c != null) {
            Log.i("hasil-title", c.getString(titleIndex));
            Log.i("hasil-overview", c.getString(overviewIndex));
            Log.i("hasil-posterLink", c.getString(posterLinkIndex));
            Log.i("hasil-releaseDate", c.getString(releaseDateIndex));
            Log.i("hasil-id", c.getString(idIndex));
            Log.i("hasil-isMovie", c.getString(isMovieIndex));

            c.moveToNext();


        }


    }

    public void searchMovie(String namaMovie) {


        String query = namaMovie;
        String BASE_URL = "https://api.themoviedb.org";
        String API_KEY = "b9239506432ea4c54f8b16f4a3679008";
        String LANGUAGE = "en-us";
        final String CATEGORY = "movie";
        final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HasilSarchMovieInterface hasilSarchMovieInterface = retrofit.create(HasilSarchMovieInterface.class);
        Call<MovieSearch> call = hasilSarchMovieInterface.listSearchOfMovie(CATEGORY, API_KEY, LANGUAGE, query);
        call.enqueue(new Callback<MovieSearch>() {
            @Override
            public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {
                MovieSearch results = response.body();
                List<MovieSearch.ResultsBean> listOfMovies = results.getResults();

                for (MovieSearch.ResultsBean resultsBean : listOfMovies) {
                    double id = resultsBean.getId();
                    String title = resultsBean.getTitle();
                    String overview = resultsBean.getOverview();
                    String releaseDate = resultsBean.getRelease_date();
                    String posterLink = POSTER_BASE_URL + resultsBean.getPoster_path();
                    Movie movie = new Movie(id, title, overview, posterLink, releaseDate, true);
                    Log.i("search movie id", String.valueOf(movie.getId()));
                    Log.i("search movie judul", movie.getTitle());
                    Log.i("search movie overview", movie.getOverview());
                    Log.i("search movie release", movie.getReleaseDate());
                    Log.i("search movie posterLink", movie.getPosterLink());

                    movieList.add(movie);
                }
                Intent i = new Intent(MainActivity.this, SearchResultActivity.class);
                i.putExtra("movieList", movieList);
                startActivity(i);
//299534


            }

            @Override
            public void onFailure(Call<MovieSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void searchTv(String namaSerialTv) {

        String query = namaSerialTv;
        String BASE_URL = "https://api.themoviedb.org";
        String API_KEY = "b9239506432ea4c54f8b16f4a3679008";
        String LANGUAGE = "en-us";
        final String CATEGORY = "tv";
        final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HasilSearchSerialTvInterface hasilSearchSerialTvInterface = retrofit.create(HasilSearchSerialTvInterface.class);
        Call<SerialTvSearch> call = hasilSearchSerialTvInterface.listSearchSerialTv(CATEGORY, API_KEY, LANGUAGE, query);
        call.enqueue(new Callback<SerialTvSearch>() {
            @Override
            public void onResponse(Call<SerialTvSearch> call, Response<SerialTvSearch> response) {
                SerialTvSearch results = response.body();
                List<SerialTvSearch.ResultsBean> listOfSerialTv = results.getResults();

                for (SerialTvSearch.ResultsBean resultsBean : listOfSerialTv) {
                    double id = resultsBean.getId();
                    String title = resultsBean.getName();
                    String overview = resultsBean.getOverview();
                    String releaseDate = resultsBean.getFirst_air_date();
                    String posterLink = POSTER_BASE_URL + resultsBean.getPoster_path();
                    Movie serialTv = new Movie(id, title, overview, posterLink, releaseDate, false);
                    Log.i("search tv id", String.valueOf(serialTv.getId()));
                    Log.i("search tv judul", serialTv.getTitle());
                    Log.i("search tv overview", serialTv.getOverview());
                    Log.i("search tv release", serialTv.getReleaseDate());
                    Log.i("search tv posterLink", serialTv.getPosterLink());

                    serialTvList.add(serialTv);
                }
                Intent i = new Intent(MainActivity.this, SearchResultActivity.class);
                i.putExtra("serialTvList", serialTvList);
                startActivity(i);
//299534


            }

            @Override
            public void onFailure(Call<SerialTvSearch> call, Throwable t)
            {
                t.printStackTrace();
            }
        });




    }
}