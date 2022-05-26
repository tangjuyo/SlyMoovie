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
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.Films;
import fr.iut.InfoFilm.model.HorizontalAdapter;

public class SearchByActor extends AppCompatActivity {

    private static final String APIKEY = "2655c76215cb310d3cbc67b6129bf9da";
    private static final String BASEIMG = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_actor);

        String Ratedurl = "https://api.themoviedb.org/3/movie/top_rated?api_key="+APIKEY+"&language=fr&page=1";
        System.out.println(Ratedurl);
        Ion.getDefault(getApplicationContext()).getConscryptMiddleware().enable(false);

        Ion.with(getApplicationContext())
                .load(Ratedurl)
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
                                        }catch (NullPointerException ageag){
                                            Log.i("erreur","l'erreur est " + z,null);
                                        }
                                    }

                                }
                            }
                            films.add(f);
                        }
                        RecyclerView list = (RecyclerView) findViewById(R.id.recyclerV);
                        list.setLayoutManager(new LinearLayoutManager(SearchByActor.this, LinearLayoutManager.HORIZONTAL,false));
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

    public void Research(View v){
        TextView t = findViewById(R.id.acteur);

        String url = "https://api.themoviedb.org/3/search/person?api_key="+ APIKEY + "&language=fr&query=" + t.getText().toString().replace(" ","%20")
                +"&page=1&include_adult=false";
        Ion.getDefault(getApplicationContext()).getConscryptMiddleware().enable(false);

        Ion.with(getApplicationContext())
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Films films = new Films();
                        for(int n = 0; n<result.getAsJsonArray("results").size();n++){
                            for (int h = 0; h< result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").size();h++){
                                Film f = null;
                                try{
                                    String title = result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("title").toString().split("\"")[1];
                                    try{
                                        f = new Film(
                                                result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("title").toString().split("\"")[1],
                                                BASEIMG + result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("poster_path").toString().split("\"")[1],
                                                Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("vote_average").toString()),
                                                result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("overview").toString().split("\"")[1]
                                        );
                                    }catch (ArrayIndexOutOfBoundsException t){
                                        try{
                                            f =new Film(
                                                    result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(0).getAsJsonObject().get("title").toString().split("\"")[1],
                                                    null,
                                                    Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("vote_average").toString()),
                                                    result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("overview").toString().split("\"")[1]

                                            );
                                        }catch (ArrayIndexOutOfBoundsException z){
                                            try {
                                                f = new Film(
                                                        result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(0).getAsJsonObject().get("title").toString().split("\"")[1],
                                                        BASEIMG + result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("poster_path").toString().split("\"")[1],
                                                        Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("vote_average").toString()),
                                                        "Pas de résumé trouvé sur l'API"
                                                );
                                            }catch (ArrayIndexOutOfBoundsException azzrh) {
                                                try {
                                                    f = new Film(
                                                            result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(0).getAsJsonObject().get("title").toString().split("\"")[1],
                                                            null,
                                                            Float.parseFloat(result.getAsJsonArray("results").get(n).getAsJsonObject().getAsJsonArray("known_for").get(h).getAsJsonObject().get("vote_average").toString()),
                                                            "Pas de résumé trouvé sur l'API"
                                                    );
                                                }catch (NullPointerException eg){
                                                    Log.i("erreur","l'erreur est " + z,null);
                                                }
                                            }

                                        }
                                    }
                                }catch (NullPointerException z){
                                    f  = null;
                                }
                                if (f==null){

                                }else{
                                    films.add(f);
                                }
                            }
                        }
                        Intent t = new Intent(SearchByActor.this,ShowResult.class);
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