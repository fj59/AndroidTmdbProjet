package com.example.flo.tmdbprojetflorianjacquin.Objet;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.flo.tmdbprojetflorianjacquin.util.statics;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jose on 10/6/15.
 */
public class Film implements Parcelable {

    private String title;


    @SerializedName("original_title")
    private String titleOriginal;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private int id;


    public Film() {
    }

    protected Film(Parcel in) {
        title = in.readString();
        titleOriginal = in.readString();
        poster = in.readString();
        rating = in.readString();
        id = in.readInt();
        description = in.readString();
        backdrop = in.readString();
        releaseDate = in.readString();
    }

    public void transformDate()
    {
        String date=releaseDate.substring(8,10)+"/"+releaseDate.substring(5,7)+"/"+releaseDate.substring(0,4);
        releaseDate=date;
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public void setTitleOriginal(String titleOriginal) {
        this.titleOriginal = titleOriginal;
    }

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
        if(releaseDate.contains("-"))
                transformDate();
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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
        parcel.writeString(titleOriginal);
        parcel.writeString(poster);
        parcel.writeString(rating);
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeString(backdrop);
        parcel.writeString(releaseDate);

    }

    public static class FilmResult {
        private List<Film> results;

        public List<Film> getResults() {
            return results;
        }
    }


}
