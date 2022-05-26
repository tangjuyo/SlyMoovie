package fr.iut.InfoFilm.model;

import java.util.ArrayList;

public class HistoSingleton {
    private static HistoSingleton instance;
    private ArrayList<Film> films = null;


    private HistoSingleton(Film f) {
        // The following code emulates slow initialization.
        films = new ArrayList<Film>();
    }

    private void add(Film f){
        if(!films.contains(f))
            films.add(f);
    }
    public static ArrayList<Film> getall(){
        if (instance == null){
            return null;
        }
        return instance.films;
    }

    public static void vider(){
        instance = null;
    }
    public static void addToSingleton(Film f) {
        if (instance == null) {
            instance = new HistoSingleton(f);
        }
        instance.add(f);
    }
}
