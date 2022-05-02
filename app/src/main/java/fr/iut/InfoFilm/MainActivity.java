package fr.iut.InfoFilm;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.FilmAdapter;
import fr.iut.InfoFilm.model.Films;


public class MainActivity extends AppCompatActivity {

    private static final String APIKEY = "2655c76215cb310d3cbc67b6129bf9da";
    private static final String BASEIMG = "https://image.tmdb.org/t/p/w500";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ion.getDefault(getApplicationContext()).getConscryptMiddleware().enable(false);
        ApiRequestPopulaire();
        ApiRequestUpcoming();

    }

        // initialisation de la liste déroulante des genres disponibles sur l'API.
        /*
        nbrerecherche = findViewById(R.id.nbreRecherche);
        nbrerecherche.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int number = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                number = i/10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this,"Nombre de recherche souhaité : " +number + "",Toast.LENGTH_SHORT).show();
            }
        });
        */


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
                                                null,
                                                Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().get("vote_average").toString()),
                                                null
                                        );
                                    }catch (NullPointerException z){
                                        Log.i("erreur","l'erreur est " + z,null);
                                    }
                                }

                            }
                            films.add(f);
                        }

                        ListView listviewHistorique = findViewById(R.id.listviewHistorique);
                        FilmAdapter adapter = new FilmAdapter(getApplicationContext(),films.getAll());
                        listviewHistorique.setAdapter(adapter);
                        listviewHistorique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent t = new Intent(MainActivity.this, FilmDetail.class);
                                t.putExtra("film", ((Film)adapterView.getItemAtPosition(i)));
                                startActivity(t);
                                finish();
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
                        System.out.println(result.getAsJsonArray("results").size());
                        Films films = new Films();
                        for(int n = 0; n<result.getAsJsonArray("results").size();n++){
                            System.out.println(result.getAsJsonArray("results").get(n).getAsJsonObject().get("title").toString());
                            System.out.println(result.getAsJsonArray("results").get(n).getAsJsonObject().get("poster_path").toString());
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
                                t.putExtra("film", ((Film)adapterView.getItemAtPosition(i)));
                                startActivity(t);
                                finish();
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
                finish();
                return true;
            case R.id.upComing:
                Intent historique = new Intent(this,Historique.class);
                startActivity(historique);
                finish();
                return true;
            case R.id.motcle:
                Intent recherchekeywords = new Intent(this,SearchByKeyword.class);
                startActivity(recherchekeywords);
                finish();
                return true;
            case R.id.RecherchePerso:
                Intent RecherchePerso = new Intent(this,FullSearchDetail.class);
                startActivity(RecherchePerso);
                finish();
                return true;
            case R.id.rechercheActeur:
                Intent rechercheActeur = new Intent(this,SearchByActor.class);
                startActivity(rechercheActeur);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}