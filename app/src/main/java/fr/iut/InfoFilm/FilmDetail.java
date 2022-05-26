package fr.iut.InfoFilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.Films;
import fr.iut.InfoFilm.model.HistoSingleton;
import fr.iut.InfoFilm.model.HorizontalAdapter;

public class FilmDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        Film film = null;
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("film")){
                film = intent.getParcelableExtra("film");
            }
        }

        saveData(HistoSingleton.getall());
        ImageView img = findViewById(R.id.imgFilm);
        if(film.geturl() == null){
            Picasso.get()
                    .load("https://images.unsplash.com/photo-1625457671853-5745024cf1cf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=478&q=80")
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(img);
        }else{
            Picasso.get()
                    .load(film.geturl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(img);
        }

        RatingBar rat = findViewById(R.id.note);
        rat.setRating((film.getNotes()/2));

        TextView title = findViewById(R.id.titreFilm);
        title.setText(film.getName());

        TextView resume = findViewById(R.id.resumee);
        resume.setText(film.getResumee());
    }


    private void saveData(ArrayList f) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(f);
        prefsEditor.putString("Histo",json);
        prefsEditor.commit();
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