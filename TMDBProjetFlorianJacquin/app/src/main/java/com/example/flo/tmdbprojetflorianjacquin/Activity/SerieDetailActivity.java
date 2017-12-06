package com.example.flo.tmdbprojetflorianjacquin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flo.tmdbprojetflorianjacquin.Objet.Film;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Serie;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Video;
import com.example.flo.tmdbprojetflorianjacquin.R;
import com.example.flo.tmdbprojetflorianjacquin.adapter.FilmAdapter;
import com.example.flo.tmdbprojetflorianjacquin.adapter.SerieAdapter;
import com.example.flo.tmdbprojetflorianjacquin.service.impl.ApiServiceImpl;
import com.example.flo.tmdbprojetflorianjacquin.util.statics;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class SerieDetailActivity extends AppCompatActivity {
    final public static Integer EXTRA_ID = 0;
    final public static String EXTRA_LANGUE = "EXTRA_LANGUE";
    ImageButton buttonShare,buttonFav,buttonFavOk;
    Serie film;

    Video video;
    TextView tvTitre, tvTireOriginal, tvDate, tvNote, tvDescription;
    ImageView image, backdrop;
    ApiServiceImpl api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiServiceImpl();
        video = new Video();
        Intent intent = getIntent();
        film = intent.getExtras().getParcelable(SerieAdapter.EXTRA_SERIE);
        Locale locale = new Locale(statics.langue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_serie_detail);
        buttonShare = (ImageButton) findViewById(R.id.action_share);
        tvTitre = (TextView) findViewById(R.id.tv_titre);
        tvTireOriginal = (TextView) findViewById(R.id.tv_original);
        tvNote = (TextView) findViewById(R.id.tv_note);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        image = (ImageView) findViewById(R.id.image);
        backdrop = (ImageView) findViewById(R.id.backdrop);

        tvTitre.setText(film.getTitle());
        tvTireOriginal.setText(film.getTitreOriginal());
        if (statics.langue.contains("fr")) {
            tvNote.setText("note : " + film.getRating() + "/10");
        }
        if (statics.langue.contains("en")) {
            tvNote.setText("rate: " + film.getRating() + "/10");
        }
        tvDate.setText(film.getReleaseDate());
        tvDescription.setText(film.getDescription());
        buttonFav= (ImageButton) findViewById(R.id.imageFavoris);
        buttonFavOk= (ImageButton) findViewById(R.id.imageFavorisOk);



        Gson gson = new Gson();
        if(statics.mesSeriesFavoris!=null) {
            Map mov = statics.mesSeriesFavoris.getAll();
            Iterator it = mov.keySet().iterator();
            while (it.hasNext()) {
                Object idt = it.next();
                Object ob = mov.get(idt);
                Serie movie = gson.fromJson((String) ob, Serie.class);
                if(movie.getId()==film.getId()){
                    buttonFav.setVisibility(View.INVISIBLE);
                    buttonFavOk.setVisibility(View.VISIBLE);

                }
            }
        }
        getVideo();



        Picasso.with(this)
                .load(film.getBackdrop())
                .error(R.drawable.ic_error)
                .into(backdrop);

    }



    void favoris(View v)
    {
        buttonFav.setVisibility(View.INVISIBLE);
        buttonFavOk.setVisibility(View.VISIBLE);
        statics.mesSeriesFavoris = getPreferences(MODE_PRIVATE );
        SharedPreferences.Editor favEditor = statics.mesSeriesFavoris.edit();
        Gson gson = new Gson();
        String json = gson.toJson(film);
        favEditor.putString(film.getId()+"", json);
        favEditor.commit();

    }

    void favorisSupr(View v)
    {
        buttonFav.setVisibility(View.VISIBLE);
        buttonFavOk.setVisibility(View.INVISIBLE);
        Gson gson = new Gson();
        if(statics.mesSeriesFavoris!=null) {
            Map mov = statics.mesSeriesFavoris.getAll();
            Iterator it = mov.keySet().iterator();
            while (it.hasNext()) {
                Object idt = it.next();
                Object ob = mov.get(idt);
                Serie movie = gson.fromJson((String) ob, Serie.class);
                if(movie.getId()==film.getId()){
                    statics.mesSeriesFavoris.edit().remove(film.getId()+"").commit();

                }
            }
        }

    }

    void getVideo(){
        api.getVideoMovies(new ApiServiceImpl.CustomCallBackVideo() {
            @Override
            public void onSuccess(Video.VideoResult videos) {
                if(videos!=null) {
                    if (videos.getResults() != null) {
                        if (videos.getResults().size() > 0) {
                            video = videos.getResults().get(0);
                            ImageButton video = (ImageButton) findViewById(R.id.imageButton);
                            video.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onError(String message) {

            }

        },statics.langue,film.getId());
    }


    public void watchYoutubeVideo(View V){
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getKey()));
        startActivity(webIntent);
    }

    public void share(View view) {
        Log.i("info", "Button share ok !");
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody ="";
        if(statics.langue.contains("fr"))
        {
            shareBody ="Ce film devrait t'interresser : \n"+film.getTitle()+"\n"+video.getKey() ;
        }
        if(statics.langue.contains("en"))
        {
            shareBody ="This film should interest you: \n"+film.getTitle()+"\n"+video.getKey() ;
        }
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
