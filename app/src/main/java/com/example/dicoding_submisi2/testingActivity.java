package com.example.dicoding_submisi2;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dicoding_submisi2.contentProvider.ContactProvider;

public class testingActivity extends AppCompatActivity {
    EditText titleEditText, overviewEditText, posterLinkEditText, releaseEditText, idEditText, isMovieEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        titleEditText = findViewById(R.id.title);
        overviewEditText = findViewById(R.id.overview);
        posterLinkEditText = findViewById(R.id.posterLink);
        releaseEditText = findViewById(R.id.releaseDate);
        idEditText = findViewById(R.id.id);
        isMovieEditText = findViewById(R.id.isMovie);
    }

    public void add(View v){
        String title = titleEditText.getText().toString();
        String overView = overviewEditText.getText().toString();
        String posterLink = posterLinkEditText.getText().toString();
        String releaseDate = releaseEditText.getText().toString();
        String id = idEditText.getText().toString();
        String isMovie = isMovieEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(ContactProvider.title, title);
        values.put(ContactProvider.overview, overView);
        values.put(ContactProvider.posterLink, posterLink);
        values.put(ContactProvider.releaseDate, releaseDate);
        values.put(ContactProvider.id, id);
        values.put(ContactProvider.isMovie, isMovie);

        // Provides access to other applications Content Providers
        Uri uri = getContentResolver().insert(ContactProvider.CONTENT_URL, values);


    }


}
