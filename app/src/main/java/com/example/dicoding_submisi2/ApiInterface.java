package com.example.dicoding_submisi2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/{jenis}/{category}")
    Call<MovieResults> listOfMovie(
            @Path("jenis") String jenis,
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );



}
