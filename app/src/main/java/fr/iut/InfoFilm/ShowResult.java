package fr.iut.InfoFilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.FilmAdapter;
import fr.iut.InfoFilm.model.Films;

public class ShowResult extends AppCompatActivity {

    private Films tab;
    private String BASEIMG = "https://image.tmdb.org/t/p/w500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("tab")){
                tab = intent.getParcelableExtra("tab");
            }
        }
        ListView sp = findViewById(R.id.listv);
        FilmAdapter adapter = new FilmAdapter(this,tab.getAll());
        sp.setAdapter(adapter);
        sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent t = new Intent(ShowResult.this, FilmDetail.class);
                t.putExtra("film", ((Film)adapterView.getItemAtPosition(i)));
                startActivity(t);
                finish();
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