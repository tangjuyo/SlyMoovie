package fr.iut.InfoFilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.FilmAdapter;
import fr.iut.InfoFilm.model.Films;
import fr.iut.InfoFilm.model.HorizontalAdapter;

public class FullSearchDetail extends AppCompatActivity {

    private ArrayList<JsonObject> genres = new ArrayList<>();
    private static final String APIKEY = "2655c76215cb310d3cbc67b6129bf9da";
    private static final String BASEIMG = "https://image.tmdb.org/t/p/w500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_search_detail);

        String urlGenre = "https://api.themoviedb.org/3/genre/movie/list?api_key="+APIKEY+"&language=fr";
        Spinner spin = findViewById(R.id.spinner);
        Ion.with(getApplicationContext())
                .load(urlGenre)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArrayList<String> tab = new ArrayList<>();
                        tab.add("...");
                        for( int t =0;t < result.getAsJsonArray("genres").size();t++){
                            tab.add(result.getAsJsonArray("genres").get(t).getAsJsonObject().get("name").toString().split("\"")[1]);
                            genres.add(result.getAsJsonArray("genres").get(t).getAsJsonObject());
                        }
                        Spinner spinGenre = findViewById(R.id.spinner);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.simple_spinner,tab);
                        spinGenre.setAdapter(adapter);
                        saveTab(genres);
                    }
                });

        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key="+APIKEY+"&language=fr&page=1";
        Ion.getDefault(getApplicationContext()).getConscryptMiddleware().enable(false);

        Ion.with(getApplicationContext())
                .load(url)
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
                        RecyclerView list = (RecyclerView) findViewById(R.id.recyclerV);
                        list.setLayoutManager(new LinearLayoutManager(FullSearchDetail.this, LinearLayoutManager.HORIZONTAL,false));
                        list.setAdapter(new HorizontalAdapter(films.getAll()));
                        list.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                            @Override
                            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                                return false;
                            }

                            @Override
                            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            }

                            @Override
                            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                            }
                        });
                    }
                });

    }

    public void saveTab(ArrayList<JsonObject> obj){
        this.genres.addAll(obj);
        System.out.println(this.genres);
    }

    public void SearchChampsOpt(View v){
        String baseurl = "https://api.themoviedb.org/3/discover/movie?api_key="+APIKEY+"&language=fr&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

        if (!((TextView) findViewById(R.id.editTextTextPersonName)).getText().toString().equals(""))
            baseurl += ("&with_keywords=" + ((TextView) findViewById(R.id.editTextTextPersonName)).getText().toString().replace(" ","%20"));
        if (!((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString().equals("...")){
            for(JsonObject t : this.genres){
                if(t.get("name").toString().split("\"")[1].equals(((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString()))
                    baseurl += ("&with_genres=" + t.get("id").toString());
            }
        }
        if(!((TextView) findViewById(R.id.editTextDate)).getText().toString().equals(""))
            baseurl += ("&year=" + ((TextView) findViewById(R.id.editTextDate)).getText().toString());

        baseurl += "&with_watch_monetization_types=flatrate";
        System.out.println(baseurl);
        Ion.getDefault(getApplicationContext()).getConscryptMiddleware().enable(false);

        Ion.with(getApplicationContext())
                .load(baseurl)
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
                                        }catch (NullPointerException eg){
                                            Log.i("erreur","l'erreur est " + z,null);
                                        }
                                    }

                                }
                            }
                            films.add(f);
                        }
                        Intent t = new Intent(FullSearchDetail.this,ShowResult.class);
                        t.putExtra("tab",films);
                        startActivity(t);
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