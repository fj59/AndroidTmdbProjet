package com.example.flo.tmdbprojetflorianjacquin.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flo.tmdbprojetflorianjacquin.Objet.Film;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Serie;
import com.example.flo.tmdbprojetflorianjacquin.R;
import com.example.flo.tmdbprojetflorianjacquin.adapter.FilmAdapter;
import com.example.flo.tmdbprojetflorianjacquin.adapter.SerieAdapter;
import com.example.flo.tmdbprojetflorianjacquin.service.impl.ApiServiceImpl;
import com.example.flo.tmdbprojetflorianjacquin.util.statics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM;
import static android.view.MenuItem.SHOW_AS_ACTION_NEVER;

public class FilmSeriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private String type="film";

    MenuItem mItemL, mItemG,mItemD;
    TextView tvPages;
    List<Film> films;
    List<Serie> series;
    String vue;
    ApiServiceImpl apiService;
    SearchManager searchManager;

    @Override
    public void recreate() {
        super.recreate();
        Locale locale = new Locale(statics.langue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_films_series);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale(statics.langue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        apiService=new ApiServiceImpl();
        getPopularFilms();
        setContentView(R.layout.activity_films_series);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvPages=(TextView)findViewById(R.id.tvPages);
        tvPages.setText("page : "+statics.page+"");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        vue="grille";


    }

    public void next(View v)
    {
        if(statics.page<999) {
            statics.page++;
            if(type.contains("film"))
            {
                getPopularFilms();
            }
            if(type.contains("serie"))
            {
                getPopularSeries();
            }
            tvPages.setText("page : "+statics.page + "");
        }
    }
    public void prev(View v)
    {
        if(statics.page>1)
        {
            statics.page--;
            if(type.contains("film"))
            {
                getPopularFilms();
            }
            if(type.contains("serie"))
            {
                getPopularSeries();
            }
            tvPages.setText("page : "+statics.page+"");
        }
    }

    public void getPopularFilms(){
        apiService.getPopularFilms(new ApiServiceImpl.CustomCallBack<Film>() {
            @Override
            public void onSuccess(List<Film> movies) {
                films=movies;
                if(films!=null)
                {
                    if (films.size() != 0)
                    {
                        if (vue.contains("liste"))
                            setViewList(films);
                        if (vue.contains("grille"))
                            setViewGrid(films);
                        if (vue.contains("description"))
                            setViewDescription(films);
                    }
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(FilmSeriesActivity.this,message,Toast.LENGTH_SHORT).show();
                Log.i("FILMERROR",message);
            }
        },statics.langue,statics.page);

    }

    public void getPopularSeries(){
        apiService.getPopularSeries(new ApiServiceImpl.CustomCallBack<Serie>() {
            @Override
            public void onSuccess(List<Serie> movies) {
                series=movies;
                Log.i("SERRIES succes",movies.get(0).getTitle());
                if(films!=null)
                {
                    if (films.size() != 0)
                    {
                        if (vue.contains("liste"))
                            setViewListSerie(series);
                        if (vue.contains("grille"))
                            setViewGridSerie(series);
                        if (vue.contains("description"))
                            setViewDescriptionSerie(series);
                    }
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(FilmSeriesActivity.this,message,Toast.LENGTH_SHORT).show();
                Log.i("SERRIES ERROR",message);
            }
        },statics.langue,statics.page);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        mItemL = menu.findItem(R.id.vue_liste);
        mItemG = menu.findItem(R.id.vue_grille);
        mItemD = menu.findItem(R.id.vue_description);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        // Assumes current activity is the searchable activity

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the query.
                doMySearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text is changed by the user.
                return true;
            }
        });

        return true;
    }
    public void doMySearch(String query){
        if(type.contains("film")) {
            apiService.searchMovies(new ApiServiceImpl.CustomCallBack<Film>() {
                @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void onSuccess(List<Film> listMovies) {
                    Film movies = new Film();
                    films = listMovies;
                    if (films != null) {
                        if (films.size() != 0) {
                            if (vue.contains("liste"))
                                setViewList(films);
                            if (vue.contains("grille"))
                                setViewGrid(films);
                            if (vue.contains("description"))
                                setViewDescription(films);
                        }
                    }

                }

                @Override
                public void onError(String message) {
                    Toast.makeText(FilmSeriesActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }, query);
        }else
        {
            apiService.searchSeries(new ApiServiceImpl.CustomCallBack<Serie>() {
                @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void onSuccess(List<Serie> listMovies) {
                    series = listMovies;
                    if (series != null) {
                        if (series.size() != 0) {
                            if (vue.contains("liste"))
                                setViewListSerie(series);
                            if (vue.contains("grille"))
                                setViewGridSerie(series);
                            if (vue.contains("description"))
                                setViewDescriptionSerie(series);
                        }
                    }

                }

                @Override
                public void onError(String message) {
                    Toast.makeText(FilmSeriesActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }, query);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.vue_grille) {
            vue="grille";
            if(type.contains("film"))
                setViewGrid(films);
            else
                setViewGridSerie(series);
            mItemG.setShowAsAction(SHOW_AS_ACTION_IF_ROOM);
            mItemL.setShowAsAction(SHOW_AS_ACTION_NEVER);
            mItemD.setShowAsAction(SHOW_AS_ACTION_NEVER);
            return true;
        }
        if (id == R.id.vue_liste) {
            vue="liste";
            if(type.contains("film"))
                setViewList(films);
            else
                setViewListSerie(series);
            mItemG.setShowAsAction(SHOW_AS_ACTION_NEVER);
            mItemL.setShowAsAction(SHOW_AS_ACTION_IF_ROOM);
            mItemD.setShowAsAction(SHOW_AS_ACTION_NEVER);
            return true;
        }
        if (id == R.id.vue_description) {
            vue="description";
            if(type.contains("film"))
                setViewDescription(films);
            else
                setViewDescriptionSerie(series);
            mItemG.setShowAsAction(SHOW_AS_ACTION_NEVER);
            mItemL.setShowAsAction(SHOW_AS_ACTION_NEVER);
            mItemD.setShowAsAction(SHOW_AS_ACTION_IF_ROOM);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_films) {
            type="film";
            statics.page=1;
            getPopularFilms();
            // Handle the camera action
        } else if (id == R.id.nav_series) {
            type="serie";
            statics.page=1;
            getPopularSeries();

        } else if (id == R.id.nav_favoris_film) {


            Gson gson = new Gson();
            if(statics.mesFilmsFavoris!=null) {
                Map mov = statics.mesFilmsFavoris.getAll();
                Iterator it = mov.keySet().iterator();
                List<Film> movies=new ArrayList<>();
                while (it.hasNext()) {
                    Object idt = it.next();
                    Object ob = mov.get(idt);
                    Film movie = gson.fromJson((String) ob, Film.class);
                    movies.add(movie);
                }
                films=movies;
                if(films!=null)
                {
                    if (films.size() != 0)
                    {
                        if (vue.contains("liste"))
                            setViewList(films);
                        if (vue.contains("grille"))
                            setViewGrid(films);
                        if (vue.contains("description"))
                            setViewDescription(films);
                    }
                    else
                    {
                        if(statics.langue.contains("fr"))
                        {
                            Toast.makeText(this, "Aucun favoris !",Toast.LENGTH_LONG).show();
                        }
                        if(statics.langue.contains("en"))
                        {
                            Toast.makeText(this, "Nothing into favorite !",Toast.LENGTH_LONG).show();;
                        }
                    }
                }
                else
                {
                    if(statics.langue.contains("fr"))
                    {
                        Toast.makeText(this, "Aucun favoris !",Toast.LENGTH_LONG).show();;
                    }
                    if(statics.langue.contains("en"))
                    {
                        Toast.makeText(this, "Nothing into favorite !",Toast.LENGTH_LONG).show();;
                    }
                }
            }
            else
            {
                if(statics.langue.contains("fr"))
                {
                    Toast.makeText(this, "Aucun favoris !",Toast.LENGTH_LONG).show();;
                }
                if(statics.langue.contains("en"))
                {
                    Toast.makeText(this, "Nothing into favorite !",Toast.LENGTH_LONG).show();;
                }
            }
        }else if (id == R.id.nav_favoris_serie) {


            Gson gson = new Gson();
            if(statics.mesSeriesFavoris!=null) {
                Map mov = statics.mesSeriesFavoris.getAll();
                Iterator it = mov.keySet().iterator();
                List<Serie> movies=new ArrayList<>();
                while (it.hasNext()) {
                    Object idt = it.next();
                    Object ob = mov.get(idt);
                    Serie movie = gson.fromJson((String) ob, Serie.class);
                    movies.add(movie);
                }
                series=movies;
                if(films!=null)
                {
                    if (series.size() != 0)
                    {
                        if (vue.contains("liste"))
                            setViewListSerie(series);
                        if (vue.contains("grille"))
                            setViewGridSerie(series);
                        if (vue.contains("description"))
                            setViewDescriptionSerie(series);
                    }
                    else
                    {
                        if(statics.langue.contains("fr"))
                        {
                            Toast.makeText(this, "Aucun favoris !",Toast.LENGTH_LONG).show();
                        }
                        if(statics.langue.contains("en"))
                        {
                            Toast.makeText(this, "Nothing into favorite !",Toast.LENGTH_LONG).show();;
                        }
                    }
                }
                else
                {
                    if(statics.langue.contains("fr"))
                    {
                        Toast.makeText(this, "Aucun favoris !",Toast.LENGTH_LONG).show();;
                    }
                    if(statics.langue.contains("en"))
                    {
                        Toast.makeText(this, "Nothing into favorite !",Toast.LENGTH_LONG).show();;
                    }
                }
            }
            else
            {
                if(statics.langue.contains("fr"))
                {
                    Toast.makeText(this, "Aucun favoris !",Toast.LENGTH_LONG).show();;
                }
                if(statics.langue.contains("en"))
                {
                    Toast.makeText(this, "Nothing into favorite !",Toast.LENGTH_LONG).show();;
                }
            }
        } else if (id == R.id.nav_parametres) {
            Intent intent = new Intent(this, paramActivity.class);
            startActivityForResult(intent,0);
            this.recreate();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);
        recreate();
    }


    public void setViewGrid(List<Film> films)
    {
        RecyclerView mRvFilms = (RecyclerView) findViewById(R.id.rv_films);


        FilmAdapter filmAdapter = new FilmAdapter(this, films,vue);
        GridLayoutManager manager = new GridLayoutManager(this,4, GridLayoutManager.VERTICAL, false);
        mRvFilms.setLayoutManager(manager);

        mRvFilms.setAdapter(filmAdapter);


    }

    public void setViewGridSerie(List<Serie> serie)
    {
        RecyclerView mRvFilms = (RecyclerView) findViewById(R.id.rv_films);


        GridLayoutManager manager = new GridLayoutManager(this,4, GridLayoutManager.VERTICAL, false);
        mRvFilms.setLayoutManager(manager);

        SerieAdapter serieAdapter = new SerieAdapter(this, serie,vue);
        mRvFilms.setAdapter(serieAdapter);


    }

    public void setViewList(List<Film> films)
    {

        RecyclerView mRvFilms = (RecyclerView) findViewById(R.id.rv_films);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvFilms.setLayoutManager(layoutManager);

        FilmAdapter filmAdapter = new FilmAdapter(this, films,vue);
        mRvFilms.setAdapter(filmAdapter);


    }
    public void setViewListSerie(List<Serie> serie)
    {

        RecyclerView mRvFilms = (RecyclerView) findViewById(R.id.rv_films);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvFilms.setLayoutManager(layoutManager);

        SerieAdapter serieAdapter = new SerieAdapter(this, serie,vue);
        mRvFilms.setAdapter(serieAdapter);


    }
    public void setViewDescription(List<Film> films)
    {

        RecyclerView mRvFilms = (RecyclerView) findViewById(R.id.rv_films);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvFilms.setLayoutManager(layoutManager);

        FilmAdapter filmAdapter = new FilmAdapter(this, films,vue);
        mRvFilms.setAdapter(filmAdapter);


    }
    public void setViewDescriptionSerie(List<Serie> serie)
    {

        RecyclerView mRvFilms = (RecyclerView) findViewById(R.id.rv_films);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvFilms.setLayoutManager(layoutManager);

        SerieAdapter serieAdapter = new SerieAdapter(this, serie,vue);
        mRvFilms.setAdapter(serieAdapter);

    }

}
