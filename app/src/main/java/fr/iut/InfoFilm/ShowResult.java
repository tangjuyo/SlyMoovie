package fr.iut.InfoFilm;

import android.app.Activity;
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
import fr.iut.InfoFilm.model.HistoSingleton;

public class ShowResult extends Activity {

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
                HistoSingleton.addToSingleton(((Film)adapterView.getItemAtPosition(i)));
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