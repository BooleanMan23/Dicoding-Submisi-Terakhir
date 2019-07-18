package com.example.dicoding_submisi2.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.dicoding_submisi2.SerialTvInterface;
import com.example.dicoding_submisi2.SerialTvResults;
import com.example.dicoding_submisi2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SerialTvFragment extends Fragment {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static  String API_KEY = "b9239506432ea4c54f8b16f4a3679008";
    public static   String LANGUAGE = "en-us";
    public static  String CATEGORY = "popular";
    public static  String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
    private ProgressBar progressBar;
    public Adapter movieAdapter;
    private RecyclerView movieRecyclerView;
    ArrayList<Movie> movieList = new ArrayList<>();



    public SerialTvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("wolo", "st");
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
        Log.i("serial tv fragment", "SERIAL TV FRAGMENT");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SerialTvInterface apiInterface = retrofit.create(SerialTvInterface.class);
        Call<SerialTvResults> call = apiInterface.listOfSerialTv(CATEGORY, API_KEY, LANGUAGE, PAGE);
//        https://api.themoviedb.org/3/tv/popular?api_key=b9239506432ea4c54f8b16f4a3679008&language=en-US&page=1
        call.enqueue(new Callback<SerialTvResults>() {
            @Override
            public void onResponse(Call<SerialTvResults> call, Response<SerialTvResults> response) {
                SerialTvResults results = response.body();
                List<SerialTvResults.ResultsBean> listOfSerialTv = results.getResults();

                for (SerialTvResults.ResultsBean resultsBean : listOfSerialTv){
                    double id = resultsBean.getId();
                    String title = resultsBean.getName();
                    String overview = resultsBean.getOverview();
                    String releaseDate = resultsBean.getFirst_air_date();
                    String posterLink = POSTER_BASE_URL+resultsBean.getPoster_path();
                    Movie movie = new Movie(id, title, overview, posterLink, releaseDate, false);
                    Log.i("data TV ID", String.valueOf(movie.getId()));
                    Log.i("data TV judul", movie.getTitle());
                    Log.i("data TV overview", movie.getOverview());
                    Log.i("data TV releaseDate", movie.getReleaseDate());
                    Log.i("data TV posterLink", movie.getPosterLink());
                    movieList.add(movie);

                }

                movieAdapter = new Adapter("serialtv",movieList, getActivity());
                movieRecyclerView.setAdapter(movieAdapter);

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<SerialTvResults> call, Throwable t) {
                Log.i("failur", "failure");
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

            movieAdapter = new Adapter("serialtv",movieList, getActivity());
            movieRecyclerView.setAdapter(movieAdapter);

            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAttach(Context context) {
        Log.i("onAttach", "st");
        super.onAttach(context);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        if (visible) {
            MainActivity.FRAGMENT_APA = "st";
            Log.i("visibility", "st");
        }

        super.setMenuVisibility(visible);
    }
}

