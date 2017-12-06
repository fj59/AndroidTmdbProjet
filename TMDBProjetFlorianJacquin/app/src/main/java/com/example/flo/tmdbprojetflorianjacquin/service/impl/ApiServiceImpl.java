package com.example.flo.tmdbprojetflorianjacquin.service.impl;


import com.example.flo.tmdbprojetflorianjacquin.Objet.Film;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Serie;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Video;
import com.example.flo.tmdbprojetflorianjacquin.service.ApiService;
import com.example.flo.tmdbprojetflorianjacquin.util.statics;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.flo.tmdbprojetflorianjacquin.util.Constants.API_KEY;
import static com.example.flo.tmdbprojetflorianjacquin.util.Constants.BASE_URL;


/**
 * Created by adrien on 01/09/2017.
 */

public class ApiServiceImpl {

    public static ApiService getResultApiService() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return restAdapter.create(ApiService.class);
    }


    public void getPopularFilms(final CustomCallBack<Film> customCallBack,String langue,int page) {
        final List<Film> films = new ArrayList<>();
        ApiService service = getResultApiService();


        //APPEL RETROFIT
        service.getPopularFilms(API_KEY,langue,page).enqueue(new Callback<Film.FilmResult>() {
            @Override
            public void onResponse(Call<Film.FilmResult> call, Response<Film.FilmResult> response) {

                Film.FilmResult filmResult = response.body();

                if (filmResult != null) {
                    for (Film film : filmResult.getResults()) {
                        if (film.getBackdrop() != null && film.getPoster() != null) {
                            films.add(film);
                        }
                    }
                }
                customCallBack.onSuccess(films);
            }

            @Override
            public void onFailure(Call<Film.FilmResult> call, Throwable t) {
                customCallBack.onError("Impossible de recupérer les films populaires");
            }
        });
    }

    public void getPopularSeries(final CustomCallBack<Serie> customCallBack, String langue, int page) {
        final List<Serie> series = new ArrayList<>();
        ApiService service = getResultApiService();


        //APPEL RETROFIT
        service.getPopularSeries(API_KEY,langue,page).enqueue(new Callback<Serie.SerieResult>() {
            @Override
            public void onResponse(Call<Serie.SerieResult> call, Response<Serie.SerieResult> response) {

                Serie.SerieResult serieResult = response.body();

                if (serieResult != null) {
                    for (Serie serie : serieResult.getResults()) {
                        if (serie.getBackdrop() != null && serie.getPoster() != null) {
                            series.add(serie);
                        }
                    }
                }
                customCallBack.onSuccess(series);
            }

            @Override
            public void onFailure(Call<Serie.SerieResult> call, Throwable t) {
                customCallBack.onError("Impossible de recupérer les séries populaires");
            }
        });
    }


    public void getVideoMovies(final CustomCallBackVideo customCallBackVideo, String langue, int id) {
        ApiService service = getResultApiService();



        //APPEL RETROFIT
        service.getVideoMovies(id,API_KEY,langue).enqueue(new Callback<Video.VideoResult>() {
            @Override
            public void onResponse(Call<Video.VideoResult> call, Response<Video.VideoResult> response) {

                Video.VideoResult videoResult = response.body();
                customCallBackVideo.onSuccess(videoResult);
            }

            @Override
            public void onFailure(Call<Video.VideoResult> call, Throwable t) {
                customCallBackVideo.onError("Impossible de recupérer les videos du film");
            }
        });
    }
    public void getVideoTv(final CustomCallBackVideo customCallBackVideo, String langue, int id) {
        ApiService service = getResultApiService();



        //APPEL RETROFIT
        service.getVideoTv(id,API_KEY,langue).enqueue(new Callback<Video.VideoResult>() {
            @Override
            public void onResponse(Call<Video.VideoResult> call, Response<Video.VideoResult> response) {

                Video.VideoResult videoResult = response.body();
                customCallBackVideo.onSuccess(videoResult);
            }

            @Override
            public void onFailure(Call<Video.VideoResult> call, Throwable t) {
                customCallBackVideo.onError("Impossible de recupérer les videos du film");
            }
        });
    }

    public void searchMovies(final CustomCallBack<Film> customCallBack, String recherche){
        final List<Film> movies = new ArrayList<>();
        ApiService service = getResultApiService();


        //APPEL RETROFIT
        service.searchMovies(API_KEY, recherche, statics.langue).enqueue(new Callback<Film.FilmResult>() {
            @Override
            public void onResponse(Call<Film.FilmResult> call, Response<Film.FilmResult> response) {

                Film.FilmResult movieResult = response.body();

                if (movieResult != null) {
                    for (Film movie : movieResult.getResults()) {
                        if (movie.getBackdrop() != null && movie.getPoster() != null) {
                            movies.add(movie);
                        }
                    }
                }
                customCallBack.onSuccess(movies);
            }

            @Override
            public void onFailure(Call<Film.FilmResult> call, Throwable t) {
                customCallBack.onError("La recherche n'a donné aucun résultat");
            }
        });
    }

    public void searchSeries(final CustomCallBack<Serie> customCallBack, String recherche){
        final List<Serie> movies = new ArrayList<>();
        ApiService service = getResultApiService();


        //APPEL RETROFIT
        service.searchSeries(API_KEY, recherche,statics.langue).enqueue(new Callback<Serie.SerieResult>() {
            @Override
            public void onResponse(Call<Serie.SerieResult> call, Response<Serie.SerieResult> response) {

                Serie.SerieResult movieResult = response.body();

                if (movieResult != null) {
                    for (Serie movie : movieResult.getResults()) {
                        if (movie.getBackdrop() != null && movie.getPoster() != null) {
                            movies.add(movie);
                        }
                    }
                }
                customCallBack.onSuccess(movies);
            }

            @Override
            public void onFailure(Call<Serie.SerieResult> call, Throwable t) {
                customCallBack.onError("La recherche n'a donné aucun résultat");
            }
        });
    }
    public interface CustomCallBack<T> {
        void onSuccess(List<T> movies);

        void onError(String message);
    }

    public interface CustomCallBackVideo {
        void onSuccess(Video.VideoResult movies);

        void onError(String message);
    }

}
