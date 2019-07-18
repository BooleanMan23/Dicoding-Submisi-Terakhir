package com.example.dicoding_submisi2.ui.main;


import android.content.Context;
import android.database.Cursor;
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

import com.example.dicoding_submisi2.Adapter;
import com.example.dicoding_submisi2.MainActivity;
import com.example.dicoding_submisi2.Movie;
import com.example.dicoding_submisi2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritMovieFragment extends Fragment {
    private ProgressBar progressBar;
    public Adapter movieAdapter;
    private RecyclerView movieRecyclerView;
    ArrayList<Movie> movieList ;
    public FavoritMovieFragment() {
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

        return inflater.inflate(R.layout.fragment_favorit_movie, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieRecyclerView  = getView().findViewById(R.id.rv_movie);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = getView().findViewById(R.id.progressBar);
        movieList = new ArrayList<>();
        movieList.clear();
        prepare();
        movieAdapter = new Adapter("moviefavorit",movieList, getActivity());
        movieRecyclerView.setAdapter(movieAdapter);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("movieList", movieList);
        super.onSaveInstanceState(outState);
    }

    public void prepare(){
        Cursor c = MainActivity.myDatabase.rawQuery("SELECT * FROM favorite WHERE isMovie = 1", null );
        try {

            //mengambil index kolum
            int titleIndex = c.getColumnIndex("title");
            int overviewIndex = c.getColumnIndex("overview");
            int posterLinkIndex = c.getColumnIndex("posterLink");
            int releaseDateIndex = c.getColumnIndex("releaseDate");
            int idIndex = c.getColumnIndex("id");
            int isMovieIndex = c.getColumnIndex("isMovie");
            Log.i("favoritMovie?", "===============");
            c.moveToFirst();
            while (c != null){
                String title = c.getString(titleIndex);
                String overview = c.getString(overviewIndex);
                String posterLink = c.getString(posterLinkIndex);
                String releaseDate = c.getString(releaseDateIndex);
                double id = c.getDouble(idIndex);
                String isMovie = c.getString(isMovieIndex);
                Log.i("favorit movie", title);
                Log.i("favorit movie", overview);
                Log.i("favorit movie", posterLink);
                Log.i("favorit movie", releaseDate);
                Log.i("favorit movie", String.valueOf(id));
                Log.i("favorit movie", isMovie);

                Movie movie = new Movie(id,title, overview, posterLink, releaseDate, true);
                //check if already have the same id
                boolean udahAda = false;
                for (int i = 0 ; i< movieList.size() ; i++){
                    if (id == movieList.get(i).getId()){
                        udahAda = true;
                    }

                }
                if (udahAda == false){
                    movieList.add(movie);
                }

                c.moveToNext();



            }


        }
        catch (Exception E){
            E.printStackTrace();

        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("movieList");

            movieAdapter = new Adapter("moviefavorit",movieList, getActivity());
            movieRecyclerView.setAdapter(movieAdapter);

            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAttach(Context context) {
        Log.i("onAttach", "mvf");
        super.onAttach(context);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        if (visible) {
            MainActivity.FRAGMENT_APA = "mv";
            Log.i("visibility", "mvf");
        }

        super.setMenuVisibility(visible);
    }
}