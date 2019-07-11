package com.example.dicoding_submisi2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailItemActivity extends AppCompatActivity {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static   String LANGUAGE = "en-us";
    public static  String KEY = "b9239506432ea4c54f8b16f4a3679008";
    public static  String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";
    ImageView posterImageView;
    TextView judulTextView;
    TextView deskripsiTextView;
    TextView tanggalRilisTextView;
    ProgressBar detailProgressbar;
    Button masukanFavoritButton;
    Button hapusFavoritButton;
    double sqlId;
    String judul;
    String overview;
    String releaseDate;
    String posterLink;
    Boolean apakahMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        masukanFavoritButton = (Button) findViewById(R.id.masukanFavoritButton);
        hapusFavoritButton = (Button) findViewById(R.id.removeFavoritButton);
        posterImageView = (ImageView) findViewById(R.id.detailPosterImageView);
        judulTextView = (TextView) findViewById(R.id.detailJudulFilmTextView);
        deskripsiTextView = (TextView) findViewById(R.id.detailDeskripsiTextView);
        tanggalRilisTextView = (TextView) findViewById(R.id.detailTanggalRilisFilmTextView);
        detailProgressbar = findViewById(R.id.detailProgressBar);

masukanFavoritButton.setVisibility(View.GONE);
     hapusFavoritButton.setVisibility(View.GONE);



        Intent intent = getIntent();
        final double intentId = intent.getDoubleExtra("id", 0);
        Log.i("id", String.valueOf(intentId));
        final boolean[] isMovie = {intent.getBooleanExtra("isMovie", false)};

        if (isMovie[0] == true){
            Log.i("isMovie", "true");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DetailMovieInterface apiInterface = retrofit.create(DetailMovieInterface.class);
            Call<DetailMovieResult> call = apiInterface.listOfMovie(intentId, KEY, LANGUAGE);
            call.enqueue(new Callback<DetailMovieResult>() {
                @Override
                public void onResponse(Call<DetailMovieResult> call, Response<DetailMovieResult> response) {
                    Log.i("onResponse", "onresponse");
                 DetailMovieResult result = response.body();
                 detailProgressbar.setVisibility(View.GONE);
                 double id = intentId;
                 sqlId = id;
                  judul = result.getTitle();
                  overview = result.getOverview();
                  releaseDate = result.getRelease_date();
                  posterLink = POSTER_BASE_URL+result.getPoster_path();
                apakahMovie = true;
                    sudahFavorit(id);
                 Movie movie = new Movie(id, judul, overview, posterLink, releaseDate, apakahMovie);

                 Log.i("detail movie judul", movie.getTitle());
                 Log.i("detail movie overview", movie.getOverview());
                 Log.i("detail movie rilis", movie.getReleaseDate());
                 Log.i("detail movie poster", movie.getPosterLink());
                    Picasso.get().load(movie.getPosterLink()).into(posterImageView);
                    judulTextView.setText(movie.getTitle());
                    deskripsiTextView.setText(movie.getOverview());
                    tanggalRilisTextView.setText(movie.getReleaseDate());





                }

                @Override
                public void onFailure(Call<DetailMovieResult> call, Throwable t) {
                    Log.i("failur", t.toString());

                }
            });
        }
        else {
            Log.i("isMovie", "false");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DetailSerialTvInterface apiInterface = retrofit.create(DetailSerialTvInterface.class);
            Call<DetailSerialTvResult> call = apiInterface.listOfSerialTv(intentId, KEY, LANGUAGE);
            call.enqueue(new Callback<DetailSerialTvResult>() {
                @Override
                public void onResponse(Call<DetailSerialTvResult> call, Response<DetailSerialTvResult> response) {
                    Log.i("onResponse", "onresponse");
                    DetailSerialTvResult result = response.body();
                    detailProgressbar.setVisibility(View.GONE);

                     judul = result.getName();
                     overview = result.getOverview();
                     releaseDate = result.getFirst_air_date();
                     posterLink = POSTER_BASE_URL+result.getPoster_path();
                     sqlId = result.getId();
                     apakahMovie = false;


                     sudahFavorit(sqlId);
                    Movie movie = new Movie(sqlId, judul, overview, posterLink, releaseDate,apakahMovie);
                    Log.i("detail movie judul", movie.getTitle());
                    Log.i("detail movie overview", movie.getOverview());
                    Log.i("detail movie rilis", movie.getReleaseDate());
                    Log.i("detail movie poster", movie.getPosterLink());
                    Picasso.get().load(movie.getPosterLink()).into(posterImageView);
                    judulTextView.setText(movie.getTitle());
                    deskripsiTextView.setText(movie.getOverview());
                    tanggalRilisTextView.setText(movie.getReleaseDate());





                }

                @Override
                public void onFailure(Call<DetailSerialTvResult> call, Throwable t) {
                    Log.i("failur", t.toString());

                }
            });
        }



    }

    public void sudahFavorit(double id){
        Cursor c = MainActivity.myDatabase.rawQuery("SELECT * FROM favorite", null );
        boolean sudah = false;
        try {

            //mengambil index kolum
            int titleIndex = c.getColumnIndex("title");
            int overviewIndex = c.getColumnIndex("overview");
            int posterLinkIndex = c.getColumnIndex("posterLink");
            int releaseDateIndex = c.getColumnIndex("releaseDate");
            int idIndex = c.getColumnIndex("id");
            int isMovieIndex = c.getColumnIndex("isMovie");
            Log.i("sudah?", "===============");
            c.moveToFirst();
            while (c != null){
                Log.i("s-title", c.getString(titleIndex));
                Log.i("s-overview", c.getString(overviewIndex));
                Log.i("s-posterLink", c.getString(posterLinkIndex));
                Log.i("s-releaseDate", c.getString(releaseDateIndex));
                Log.i("s-id", c.getString(idIndex));
                Log.i("s-isMovie", c.getString(isMovieIndex));
                if (id == c.getDouble(idIndex)){
                    sudah = true;
                }
                c.moveToNext();


            }


        }
        catch (Exception E){
            E.printStackTrace();
        }
        if (sudah == true){
            masukanFavoritButton.setVisibility(View.GONE);
            hapusFavoritButton.setVisibility(View.VISIBLE);
        }
        if (sudah == false){
            masukanFavoritButton.setVisibility(View.VISIBLE);
            hapusFavoritButton.setVisibility(View.GONE);
        }

    }



    public void masukanFavorit(View view){
        masukanFavoritButton.setVisibility(View.GONE);
        hapusFavoritButton.setVisibility(View.VISIBLE);
        Log.i("masukanFavorit","masukan favorit");
        try {
            Log.i("memasukan favorit", "tryt");
            //prepared statement
            String sql = "INSERT INTO favorite  (title, overview, posterLink, releaseDate, id, isMovie) VALUES (?, ?, ?, ?, ? , ?)";
            SQLiteStatement statement = MainActivity.myDatabase.compileStatement(sql);

            double dd;
            if (apakahMovie == true){
                dd = 1;
            }
            else {
                dd = 0;
            }
            statement.bindString(1, judul);
            statement.bindString(2, overview);
            statement.bindString(3, posterLink);
            statement.bindString(4, releaseDate);
            statement.bindDouble(5, sqlId);
            statement.bindDouble(6, dd);
            statement.executeInsert();

            Context context = getApplicationContext();
            CharSequence text = judul + " Berhasl ditambahkan di favorit";
            int duration = Toast.LENGTH_SHORT;
        MainActivity.favoriteId.add(sqlId);
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            MainActivity.showInLog();









        }
        catch (Exception E){
            E.printStackTrace();
        }


    }

    public  void hapusFavorit(View view){

        try {
            masukanFavoritButton.setVisibility(View.VISIBLE);
            hapusFavoritButton.setVisibility(View.GONE);
            //prepared statement
            String sql = "DELETE FROM favorite WHERE id=?";
            SQLiteStatement statement = MainActivity.myDatabase.compileStatement(sql);
            statement.bindDouble(1, sqlId);
            statement.executeUpdateDelete();
            Context context = getApplicationContext();
            CharSequence text = sqlId + " berhasil di hapus dari favorit";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            MainActivity.favoriteId.remove(sqlId);
            MainActivity.showInLog();








        }
        catch (Exception E){
            E.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
      Intent i = new Intent(DetailItemActivity.this, MainActivity.class);
      startActivity(i);
    }
}
