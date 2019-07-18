package com.example.dicoding_submisi2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    ArrayList<Movie> movieList = new ArrayList<>();
    public Adapter movieAdapter;
    private RecyclerView movieRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        movieRecyclerView  = findViewById(R.id.rv_movie);
        progressBar = findViewById(R.id.progressBar);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        if (intent.getParcelableArrayListExtra("movieList") != null) {
            movieList = intent.getParcelableArrayListExtra("movieList");
            movieAdapter = new Adapter("movie",movieList,this);
            movieRecyclerView.setAdapter(movieAdapter);
            for (int i = 0 ; i< movieList.size(); i++){

                Log.i("movie title", movieList.get(i).getTitle());
            }
            progressBar.setVisibility(View.GONE);
            Log.i("adaboi", "ada movie");
        }

        if (intent.getParcelableArrayListExtra("serialTvList") != null){
            movieList = intent.getParcelableArrayListExtra("serialTvList");
            movieAdapter = new Adapter("movie",movieList,this);
            movieRecyclerView.setAdapter(movieAdapter);
            for (int i = 0 ; i< movieList.size(); i++){

                Log.i("movie title", movieList.get(i).getTitle());
            }
            progressBar.setVisibility(View.GONE);
            Log.i("adaboi", "ada fragment");
        }

    }
}
