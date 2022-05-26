package fr.iut.InfoFilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.HistoSingleton;
import fr.iut.InfoFilm.model.TimeLineAdapter;

public class Historique extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!(readData() == null)){
            ArrayList<Film> tmp = readData();
            for(Film f : tmp){
                HistoSingleton.addToSingleton(f);
            }
        }

        setContentView(R.layout.activity_historique);
        if (!(HistoSingleton.getall()== null)){
            RecyclerView list = (RecyclerView) findViewById(R.id.listv);
            list.setLayoutManager(new LinearLayoutManager(Historique.this, LinearLayoutManager.VERTICAL,false));
            list.setAdapter(new TimeLineAdapter(HistoSingleton.getall()));
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
    }

    private void videHisto() {
        SharedPreferences  mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("Histo",null);
        HistoSingleton.vider();
        prefsEditor.commit();
    }

    private ArrayList<Film> readData() {
        SharedPreferences  mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = mPrefs.getString("Histo", "");
        Type typeMyType = new TypeToken<ArrayList<Film>>(){}.getType();
        ArrayList<Film> tab = gson.fromJson(json, typeMyType);
        return tab;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.histo_menu,menu);
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
            case R.id.viderHisto:
                videHisto();
                Intent historique = new Intent(this,Historique.class);
                startActivity(historique);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}