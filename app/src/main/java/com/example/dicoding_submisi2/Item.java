package com.example.dicoding_submisi2;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private int poster;
    private String judul;
    private  String deskripsi;
    private  String tanggalRilis;

    protected Item(Parcel in) {
        poster = in.readInt();
        judul = in.readString();
        deskripsi = in.readString();
        tanggalRilis = in.readString();
        isMovie = in.readByte() != 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public boolean getIsMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    private boolean isMovie;

    public Item(){}

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public void setTanggalRilis(String tanggalRilis) {
        this.tanggalRilis = tanggalRilis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(poster);
        dest.writeString(judul);
        dest.writeString(deskripsi);
        dest.writeString(tanggalRilis);
        dest.writeByte((byte) (isMovie ? 1 : 0));
    }
}
