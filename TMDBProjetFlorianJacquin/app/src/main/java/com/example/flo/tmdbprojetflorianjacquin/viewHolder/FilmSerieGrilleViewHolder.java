package com.example.flo.tmdbprojetflorianjacquin.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flo.tmdbprojetflorianjacquin.R;
import com.squareup.picasso.Picasso;

/**
 * Created by floja on 15/11/2017.
 */

public class FilmSerieGrilleViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_titre;
    public TextView tv_date;
    public TextView tv_description;
    public ImageView image,backdrop;

    public FilmSerieGrilleViewHolder(View itemView) {

        super(itemView);
        image=(ImageView)itemView.findViewById(R.id.image);;
        backdrop=(ImageView)itemView.findViewById(R.id.backdrop);
        tv_titre=(TextView) itemView.findViewById(R.id.tv_titre);
        tv_date=(TextView) itemView.findViewById(R.id.tv_date);
        tv_description=(TextView) itemView.findViewById(R.id.tv_description);
    }

    public void setImage(Context context, String lienImage)
    {

        Picasso.with(context)
                .load(lienImage)
                .error(R.drawable.ic_error)
                .into(image);
    }
    public void setBackdrop(Context context, String lienImage)
    {

        Picasso.with(context)
                .load(lienImage)
                .error(R.drawable.ic_error)
                .into(backdrop);
    }
}
