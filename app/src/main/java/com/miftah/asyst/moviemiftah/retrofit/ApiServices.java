package com.miftah.asyst.moviemiftah.retrofit;

import com.miftah.asyst.moviemiftah.retrofit.Response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("discover/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("year") String year, @Query("page") int page, @Query("sort_by") String sortBy);

    @GET("search/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

}
