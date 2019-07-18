package com.example.dicoding_submisi2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HasilSarchMovieInterface {

    @GET("/3/search/{category}")
    Call<MovieSearch> listSearchOfMovie(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String nama

    );
}
