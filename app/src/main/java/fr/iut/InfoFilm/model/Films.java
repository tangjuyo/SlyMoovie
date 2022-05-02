package fr.iut.InfoFilm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Films implements Parcelable  {
    private ArrayList<Film> films;

    public Films(){
        this.films = new ArrayList<>();
    }

    protected Films(Parcel in) {
        films = in.createTypedArrayList(Film.CREATOR);
    }

    public static final Creator<Films> CREATOR = new Creator<Films>() {
        @Override
        public Films createFromParcel(Parcel in) {
            return new Films(in);
        }

        @Override
        public Films[] newArray(int size) {
            return new Films[size];
        }
    };

    public void add(Film f){
        this.films.add(f);
    }
    public void remove(Film f){
        this.films.remove(f);
    }
    public Film get(int i){
        return this.films.get(i);
    }
    public Film get(String name){
        for( Film t : this.films)
            if(t.getName().equals(name))
                return t;
        return null;
    }
    public ArrayList<Film> getAll(){
        return this.films;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(films);
    }
}
