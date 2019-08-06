package com.example.dicoding_submisi2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dicoding_submisi2.contentProvider.ContactProvider;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailItemActivity extends AppCompatActivity {
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
    Movie movie;
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
        movie = intent.getExtras().getParcelable("movie");
        judul = movie.getTitle();
        overview = movie.getOverview();
        sqlId = movie.getId();
        posterLink = movie.getPosterLink();
        releaseDate = movie.getReleaseDate();
        apakahMovie = movie.getIsMovie();
        Log.i("detailmovie", judul);
        Log.i("detailmovie",overview);
        Log.i("detailmovie", String.valueOf(sqlId));
        Log.i("detailmovie", posterLink);
        Log.i("detailmovie", releaseDate);
        Log.i("detailMovie", String.valueOf(apakahMovie));
        sudahFavorit(sqlId);
        Picasso.get().load(posterLink).into(posterImageView);
        judulTextView.setText(judul);
        deskripsiTextView.setText(overview);
        tanggalRilisTextView.setText(releaseDate);
    detailProgressbar.setVisibility(View.GONE);

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


            //content provider

            String titleProvider = judul;
            String overviewProvider = overview;
            String posterLinkProvider = posterLink;
            String releaseDateProvider = releaseDate;
            String idProvider = String.valueOf(sqlId);
            String isMovieProvider = String.valueOf(dd);

            Log.i("addContactProvider", titleProvider);
            Log.i("addContactProvider", overviewProvider);
            Log.i("addContactProvider", posterLinkProvider);
            Log.i("addContactProvider", releaseDateProvider);
            Log.i("addContactProvider", idProvider);
            Log.i("addContactProvider", isMovieProvider);

            ContentValues values = new ContentValues();
            values.put(ContactProvider.title, titleProvider);
            values.put(ContactProvider.overview, overviewProvider);
            values.put(ContactProvider.posterLink, posterLinkProvider);
            values.put(ContactProvider.releaseDate, releaseDateProvider);
            values.put(ContactProvider.id, idProvider);
            values.put(ContactProvider.isMovie, isMovieProvider);
            Uri uri = getContentResolver().insert(ContactProvider.CONTENT_URL, values);




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


            //provider
            ContentResolver resolver;
            resolver = getContentResolver();
            String idToDelete = String.valueOf(sqlId);
            final Uri CONTENT_URL = Uri.parse("content://com.example.dicoding_submisi2.contentProvider.ContactProvider/favorit");
            Log.i("deleteProvider", "deleteProvider");
            long idDeleted = resolver.delete(CONTENT_URL,
                    "id = ? ", new String[]{idToDelete});



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
