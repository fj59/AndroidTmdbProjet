package com.example.flo.tmdbprojetflorianjacquin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flo.tmdbprojetflorianjacquin.Activity.FilmDetailActivity;
import com.example.flo.tmdbprojetflorianjacquin.Activity.SerieDetailActivity;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Serie;
import com.example.flo.tmdbprojetflorianjacquin.R;
import com.example.flo.tmdbprojetflorianjacquin.viewHolder.FilmSerieGrilleViewHolder;

import java.util.List;

/**
 * Created by floja on 15/11/2017.
 */

public class SerieAdapter extends RecyclerView.Adapter<FilmSerieGrilleViewHolder> {

    private Context mContext;
    private List<Serie> mSerie;
    private String mVue;
    final public static String EXTRA_SERIE= "EXTRA_SERIE";


    public SerieAdapter(Context context, List<Serie> film, String vue) {
        mContext = context;
        mSerie = film;
        mVue=vue;
    }

    @Override
    public FilmSerieGrilleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(mVue.contains("liste"))
        {
            view = inflater.inflate(R.layout.item_films_series_liste_view, viewGroup, false);

        }
        else if(mVue.contains("description"))
        {

            view = inflater.inflate(R.layout.item_film_series_description_view, viewGroup, false);
        }
        else
        {
            view = inflater.inflate(R.layout.item_films_series_grille_view, viewGroup, false);

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Click on view", Toast.LENGTH_SHORT).show();
            }
        });

        return new FilmSerieGrilleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmSerieGrilleViewHolder holder, int position) {
        final Serie film = mSerie.get(position);

        View view = holder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SerieDetailActivity.class);
                intent.putExtra(EXTRA_SERIE,film);
                view.getContext().startActivity(intent);

            }
        });
        holder.tv_titre.setText(film.getTitle());

        holder.tv_date.setText(film.getReleaseDate());


        holder.tv_description.setText(film.getDescription());

        holder.setImage(mContext,film.getPoster());

    }

    @Override
    public int getItemCount() {

            return mSerie.size();
    }
}
