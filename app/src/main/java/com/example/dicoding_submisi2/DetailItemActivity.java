package com.example.dicoding_submisi2;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        posterImageView = (ImageView) findViewById(R.id.detailPosterImageView);
        judulTextView = (TextView) findViewById(R.id.detailJudulFilmTextView);
        deskripsiTextView = (TextView) findViewById(R.id.detailDeskripsiTextView);
        tanggalRilisTextView = (TextView) findViewById(R.id.detailTanggalRilisFilmTextView);
        detailProgressbar = findViewById(R.id.detailProgressBar);
        Intent intent = getIntent();
        double id = intent.getDoubleExtra("id", 0);
        boolean isMovie = intent.getBooleanExtra("isMovie", false);

        if (isMovie == true){
            Log.i("isMovie", "true");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DetailMovieInterface apiInterface = retrofit.create(DetailMovieInterface.class);
            Call<DetailMovieResult> call = apiInterface.listOfMovie(id, KEY, LANGUAGE);
            call.enqueue(new Callback<DetailMovieResult>() {
                @Override
                public void onResponse(Call<DetailMovieResult> call, Response<DetailMovieResult> response) {
                    Log.i("onResponse", "onresponse");
                 DetailMovieResult result = response.body();
                 detailProgressbar.setVisibility(View.GONE);
                 double id = result.getId();
                 String judul = result.getTitle();
                 String overview = result.getOverview();
                 String releaseDate = result.getRelease_date();
                 String posterLink = POSTER_BASE_URL+result.getPoster_path();
                 Boolean isMovie = true;
                 Movie movie = new Movie(id, judul, overview, posterLink, releaseDate, isMovie);
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
            Call<DetailSerialTvResult> call = apiInterface.listOfSerialTv(id, KEY, LANGUAGE);
            call.enqueue(new Callback<DetailSerialTvResult>() {
                @Override
                public void onResponse(Call<DetailSerialTvResult> call, Response<DetailSerialTvResult> response) {
                    Log.i("onResponse", "onresponse");
                    DetailSerialTvResult result = response.body();
                    detailProgressbar.setVisibility(View.GONE);
                    double id = result.getId();
                    String judul = result.getName();
                    String overview = result.getOverview();
                    String releaseDate = result.getFirst_air_date();
                    String posterLink = POSTER_BASE_URL+result.getPoster_path();
                    Boolean isMovie = true;
                    Movie movie = new Movie(id, judul, overview, posterLink, releaseDate, isMovie);
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
}
