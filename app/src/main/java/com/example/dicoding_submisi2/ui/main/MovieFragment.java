package com.example.dicoding_submisi2.ui.main;


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
import com.example.dicoding_submisi2.ApiInterface;
import com.example.dicoding_submisi2.Movie;
import com.example.dicoding_submisi2.MovieResults;
import com.example.dicoding_submisi2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static  String API_KEY = "b9239506432ea4c54f8b16f4a3679008";
    public static   String LANGUAGE = "en-us";
    public static  String CATEGORY = "popular";
    public static String JENIS = "movie";
    public static  String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private ProgressBar progressBar;
    public Adapter movieAdapter;
    private RecyclerView movieRecyclerView;
    ArrayList<Movie> movieList = new ArrayList<>();


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
//        rvMovie = getView().findViewById(R.id.rv_movie);
//        rvMovie.setHasFixedSize(true);
//        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvMovie.setAdapter(movieAdapter);
        movieRecyclerView  = getView().findViewById(R.id.rv_movie);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = getView().findViewById(R.id.progressBar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MovieResults> call = apiInterface.listOfMovie(JENIS,CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsBean> listOfMovies = results.getResults();

                for (MovieResults.ResultsBean resultsBean : listOfMovies){
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

                }

                movieAdapter = new Adapter(movieList, getActivity());
                movieRecyclerView.setAdapter(movieAdapter);

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("movieList", movieList);
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("movieList");

            movieAdapter = new Adapter(movieList, getActivity());
            movieRecyclerView.setAdapter(movieAdapter);

            progressBar.setVisibility(View.GONE);
        }

    }


}
