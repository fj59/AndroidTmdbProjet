package com.example.flo.tmdbprojetflorianjacquin.service;


import com.example.flo.tmdbprojetflorianjacquin.Objet.Film;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Serie;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<Film.FilmResult> getPopularFilms(@Query("api_key") String apiKey, @Query("language") String langue, @Query("page") int page);
    @GET("tv/popular")
    Call<Serie.SerieResult> getPopularSeries(@Query("api_key") String apiKey, @Query("language") String langue, @Query("page") int page);

    @GET("movie/{movie_id}/videos")
    Call<Video.VideoResult>  getVideoMovies(@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("language") String langue);
    @GET("tv/{tv_id}/videos")
    Call<Video.VideoResult>  getVideoTv(@Path("tv_id") int id, @Query("api_key") String apiKey, @Query("language") String langue);

    @GET("search/movie")
    Call<Film.FilmResult> searchMovies(@Query("api_key") String apiKey, @Query("query") String recherche, @Query("language") String langue);
    @GET("search/tv")
    Call<Serie.SerieResult> searchSeries(@Query("api_key") String apiKey, @Query("query") String recherche, @Query("language") String langue);
}
