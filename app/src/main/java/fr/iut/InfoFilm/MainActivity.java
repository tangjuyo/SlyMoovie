package fr.iut.InfoFilm;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.FilmAdapter;
import fr.iut.InfoFilm.model.Films;
import fr.iut.InfoFilm.model.HistoSingleton;


public class MainActivity extends AppCompatActivity {

    private static final String APIKEY = "2655c76215cb310d3cbc67b6129bf9da";
    private static final String BASEIMG = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ion.getDefault(getApplicationContext()).getConscryptMiddleware().enable(false);
        ApiRequestPopulaire();
    }

    @Override
    protected void onDestroy() {
        saveData(HistoSingleton.getall());
        super.onDestroy();
    }
    private void saveData(ArrayList f) {
        SharedPreferences  mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(f);
        prefsEditor.putString("Histo",json);
        prefsEditor.commit();
    }

    @Override
    public void onBackPressed() {
        saveData(HistoSingleton.getall());
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void ShowPopulaire(View v){
        TextView sortie = findViewById(R.id.upComing);
        TextView populaire = findViewById(R.id.Populaire);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            populaire.setBackgroundColor(Color.GRAY);
            sortie.setBackgroundColor(Color.WHITE);
        }
        ApiRequestPopulaire();
    }

    public void ShowProchainesSorties(View v){
        TextView sortie = findViewById(R.id.upComing);
        TextView populaire = findViewById(R.id.Populaire);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sortie.setBackgroundColor(Color.GRAY);
            populaire.setBackgroundColor(Color.WHITE);
        }
        ApiRequestUpcoming();
    }

    public void ApiRequestUpcoming(){
        String urlGenre = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + APIKEY + "&language=fr&page=1";
        Ion.with(getApplicationContext())
                .load(urlGenre)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        System.out.println(urlGenre);
                        Films films = new Films();
                        for(int n = 0; n<result.getAsJsonArray("results").size();n++){
                            Film f = null;
                            try{
                                f = new Film(
                                        result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString().split("\"")[1],
                                        BASEIMG + result.getAsJsonArray("results").get(n).getAsJsonObject().get("poster_path").toString().split("\"")[1],
                                        Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                        result.getAsJsonArray("results").get(n).getAsJsonObject().get("overview").toString().split("\"")[1]
                                );
                            }catch (ArrayIndexOutOfBoundsException t){
                                try {
                                    f = new Film(
                                            result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString().split("\"")[1],
                                            null,
                                            Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                            result.getAsJsonArray("results").get(n).getAsJsonObject().get("overview").toString().split("\"")[1]
                                    );
                                }catch (ArrayIndexOutOfBoundsException az) {
                                    try {
                                        f = new Film(
                                                result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString().split("\"")[1],
                                                BASEIMG + result.getAsJsonArray("results").get(n).getAsJsonObject().get("poster_path").toString().split("\"")[1],
                                                Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                                "Pas de résumé trouvé sur l'API"
                                        );
                                    }catch (ArrayIndexOutOfBoundsException azzrh) {
                                        try {
                                            f = new Film(
                                                    result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString().split("\"")[1],
                                                    null,
                                                    Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                                    "Pas de résumé trouvé sur l'API"
                                            );
                                        }catch (NullPointerException z){
                                            Log.i("erreur","l'erreur est " + z,null);
                                        }
                                    }
                                }

                            }
                            films.add(f);
                        }

                        ListView listviewHistorique = findViewById(R.id.listviewPopulaire);
                        FilmAdapter adapter = new FilmAdapter(getApplicationContext(),films.getAll());
                        listviewHistorique.setAdapter(adapter);
                        listviewHistorique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent t = new Intent(MainActivity.this, FilmDetail.class);
                                HistoSingleton.addToSingleton(((Film)adapterView.getItemAtPosition(i)));
                                t.putExtra("film", ((Film)adapterView.getItemAtPosition(i)));
                                startActivity(t);
                            }
                        });
                    }
                });
    }
    public void ApiRequestPopulaire(){


        String urlGenre = "https://api.themoviedb.org/3/movie/popular?api_key=" + APIKEY + "&language=fr&page=1";
        Ion.with(getApplicationContext())
                .load(urlGenre)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Films films = new Films();
                        for(int n = 0; n<result.getAsJsonArray("results").size();n++){
                            Film f = null;
                            try{
                                f = new Film(
                                        result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString().split("\"")[1],
                                        BASEIMG + result.getAsJsonArray("results").get(n).getAsJsonObject().get("poster_path").toString().split("\"")[1],
                                        Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                        result.getAsJsonArray("results").get(n).getAsJsonObject().get("overview").toString().split("\"")[1]
                                );
                            }catch (ArrayIndexOutOfBoundsException t){
                                try{
                                    f =new Film(
                                            result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString().split("\"")[1],
                                            null,
                                            Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                            result.getAsJsonArray("results").get(n).getAsJsonObject().get("overview").toString().split("\"")[1]
                                    );
                                }catch (ArrayIndexOutOfBoundsException z){
                                    try{
                                        f =new Film(
                                                result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString().split("\"")[1],
                                                null,
                                                Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                                null
                                        );
                                    }catch (NullPointerException ezva){
                                        Log.i("erreur","l'erreur est " + z,null);
                                    }

                                }
                            }
                            films.add(f);
                        }


                        ListView listviewPopulaire = findViewById(R.id.listviewPopulaire);
                        FilmAdapter adapter = new FilmAdapter(getApplicationContext(),films.getAll());
                        listviewPopulaire.setAdapter(adapter);
                        listviewPopulaire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent t = new Intent(MainActivity.this, FilmDetail.class);
                                HistoSingleton.addToSingleton(((Film)adapterView.getItemAtPosition(i)));
                                t.putExtra("film", ((Film)adapterView.getItemAtPosition(i)));
                                startActivity(t);
                            }
                        });
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.accueil:
                Intent acceuil = new Intent(this,MainActivity.class);
                startActivity(acceuil);
                return true;
            case R.id.Historique:
                Intent historique = new Intent(this,Historique.class);
                startActivity(historique);
                return true;
            case R.id.motcle:
                Intent recherchekeywords = new Intent(this,SearchByKeyword.class);
                startActivity(recherchekeywords);
                return true;
            case R.id.RecherchePerso:
                Intent RecherchePerso = new Intent(this,FullSearchDetail.class);
                startActivity(RecherchePerso);
                return true;
            case R.id.rechercheActeur:
                Intent rechercheActeur = new Intent(this,SearchByActor.class);
                startActivity(rechercheActeur);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}