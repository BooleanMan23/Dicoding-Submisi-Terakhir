package com.example.dicoding_submisi2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    String title;
    String overview;
    String posterLink;
    String releaseDate;
    double id;
    Boolean isMovie;

    protected Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        posterLink = in.readString();
        releaseDate = in.readString();
        id = in.readDouble();
        byte tmpIsMovie = in.readByte();
        isMovie = tmpIsMovie == 0 ? null : tmpIsMovie == 1;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setIsMoive(Boolean isMovie){
        this.isMovie = isMovie;
    }

    public Boolean getIsMovie( ){
        return  isMovie;
    }

    public double getId(){
        return  id;
    }

    public  void setId(double id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie(double id, String title, String overview, String posterLink, String releaseDate, Boolean isMovie) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterLink = posterLink;
        this.releaseDate = releaseDate;
        this.isMovie = isMovie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterLink);
        dest.writeString(releaseDate);
        dest.writeDouble(id);
        dest.writeByte((byte) (isMovie == null ? 0 : isMovie ? 1 : 2));
    }
}
