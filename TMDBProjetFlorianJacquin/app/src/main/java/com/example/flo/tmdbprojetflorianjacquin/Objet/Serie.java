package com.example.flo.tmdbprojetflorianjacquin.Objet;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.flo.tmdbprojetflorianjacquin.util.statics;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jose on 10/6/15.
 */
public class Serie implements Parcelable {

    @SerializedName("name")
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("first_air_date")
    private String firstAirDate;

    public String getTitreOriginal() {
        return titreOriginal;
    }

    public void setTitreOriginal(String titreOriginal) {
        this.titreOriginal = titreOriginal;
    }

    @SerializedName("original_name")
    private String titreOriginal;

    @SerializedName("id")
    private int id;


    public Serie() {
    }

    protected Serie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        rating = in.readString();
        id = in.readInt();
        backdrop = in.readString();
        firstAirDate = in.readString();
        titreOriginal = in.readString();
    }

    public void transformDate()
    {
        String date=firstAirDate.substring(8,10)+"/"+firstAirDate.substring(5,7)+"/"+firstAirDate.substring(0,4);
        firstAirDate=date;
    }

    public static final Creator<Serie> CREATOR = new Creator<Serie>() {
        @Override
        public Serie createFromParcel(Parcel in) {
            return new Serie(in);
        }

        @Override
        public Serie[] newArray(int size) {
            return new Serie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        if (poster != null) {
            return "http://image.tmdb.org/t/p/"+ statics.qualiteI+"/"+ poster;
        }
        return null;
    }

    public String getReleaseDate() {
        if(firstAirDate.contains("-"))
                transformDate();
        return firstAirDate;
    }

    public void setReleaseDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBackdrop() {
        if (backdrop != null) {
            return "http://image.tmdb.org/t/p/"+ statics.qualiteB+"/"+ backdrop;
        }
        return null;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(rating);
        parcel.writeInt(id);
        parcel.writeString(backdrop);
        parcel.writeString(firstAirDate);
        parcel.writeString(titreOriginal);

    }

    public static class SerieResult {
        private List<Serie> results;

        public List<Serie> getResults() {
            return results;
        }
    }
}
