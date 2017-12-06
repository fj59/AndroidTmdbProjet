package com.example.flo.tmdbprojetflorianjacquin.Objet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by floja on 28/11/2017.
 */

public class Video implements Parcelable {
    private String title;

    @SerializedName("key")
    private String video;

    @SerializedName("id")
    private String id;




    public Video() {
    }

    protected Video(Parcel in) {
        title = in.readString();
        id = in.readString();
        video = in.readString();
    }


    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public void setVideo(String video) {
        this.video = video;
    }

    public String getKey() {
        if (video != null) {
            return "https://www.youtube.com/watch?v="+video;
        }
        return null;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(id);
        parcel.writeString(video);

    }


    public static class VideoResult implements Parcelable{
        @SerializedName("id")
        private int id;

        @SerializedName("results")
        private List<Video> results;

        protected VideoResult(Parcel in) {
            id = in.readInt();
            results = in.createTypedArrayList(Video.CREATOR);
        }

        public static final Creator<VideoResult> CREATOR = new Creator<VideoResult>() {
            @Override
            public VideoResult createFromParcel(Parcel in) {
                return new VideoResult(in);
            }

            @Override
            public VideoResult[] newArray(int size) {
                return new VideoResult[size];
            }
        };

        public List<Video> getResults() {
            return results;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeList(results);

        }
    }
}
