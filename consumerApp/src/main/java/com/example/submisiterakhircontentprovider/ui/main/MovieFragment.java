package com.example.submisiterakhircontentprovider.ui.main;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.submisiterakhircontentprovider.MainActivity;
import com.example.submisiterakhircontentprovider.Movie;
import com.example.submisiterakhircontentprovider.R;

/**
 * A simple {@link Fragment} subclass.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    public Adapter movieAdapter;
    private RecyclerView movieRecyclerView;
    ArrayList<Movie> movieList = new ArrayList<>();
    static final Uri CONTENT_URL = Uri.parse("content://com.example.dicoding_submisi2.contentProvider.ContactProvider/favorit");





    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("wolo", "mv");
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieRecyclerView  = getView().findViewById(R.id.rv_movie);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = getView().findViewById(R.id.progressBar);


        ContentResolver resolver;
        Context applicationContext = MainActivity.getContextOfApplication();
        resolver = applicationContext.getContentResolver();
        String [] projection = new String[]{"title", "overview","posterLink", "releaseDate", "id", "isMovie"};
        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);
        String contactList = "";
        if (cursor.moveToFirst()){
            do{
                Log.i("getContact", "==========");
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String overview = cursor.getString(cursor.getColumnIndex("overview"));
                String posterLink = cursor.getString(cursor.getColumnIndex("posterLink"));
                String releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String isMovie = cursor.getString(cursor.getColumnIndex("isMovie"));
                Log.i("getContact-title", title);
                Log.i("getContact-overview", overview);
                Log.i("getContact-posterLink", posterLink);
                Log.i("getContact-releaseDate", releaseDate);
                Log.i("getContact-id", id);
                Log.i("getContact-isMovie", isMovie);


                double _id = Double.parseDouble(id);
                boolean _isMovie;
                if (isMovie.equals("1")){
                    _isMovie = true;
                }
                else {
                    _isMovie = false;

                }
                Movie movie = new Movie(_id, title, overview, posterLink, releaseDate, _isMovie);
                if (_isMovie == true){
                    movieList.add(movie);
                }




            }while (cursor.moveToNext());
        }

        movieAdapter = new Adapter("movie",movieList, getActivity());
        movieRecyclerView.setAdapter(movieAdapter);

        progressBar.setVisibility(View.GONE);

    }




}
