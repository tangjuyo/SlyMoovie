package fr.iut.InfoFilm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Film implements Parcelable{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(name, film.name) && Objects.equals(urlImg, film.urlImg) && Objects.equals(notes, film.notes) && Objects.equals(resumee, film.resumee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, urlImg, notes, resumee);
    }

    private String name;
    private String urlImg;
    private Float notes;
    private String resumee;

    public Film(String n, String url,Float notes,String res){
        this.name = n;
        this.urlImg = url;
        this.notes = notes;
        this.resumee = res;
    }

    protected Film(Parcel in) {
        name = in.readString();
        urlImg = in.readString();
        if (in.readByte() == 0) {
            notes = null;
        } else {
            notes = in.readFloat();
        }
        resumee = in.readString();
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

    public Float getNotes(){
        return this.notes;
    }
    public String getResumee(){
        return this.resumee;
    }
    public String geturl(){
        return this.urlImg;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return "Film :" +this.getName() +" url :" + this.geturl() + " la note : "+ this.getNotes() +" le résumée :" + this.getResumee() + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(urlImg);
        if (notes == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(notes);
        }
        parcel.writeString(resumee);
    }
}
