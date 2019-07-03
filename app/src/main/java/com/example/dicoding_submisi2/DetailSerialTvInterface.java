package com.example.dicoding_submisi2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailSerialTvInterface {

    @GET("/3/tv/{id}")
    Call<DetailSerialTvResult> listOfSerialTv(
            @Path("id") double jenis,
            @Query("api_key") String api_key,
            @Query("language") String language
    );
}
