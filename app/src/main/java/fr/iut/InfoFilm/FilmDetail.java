package fr.iut.InfoFilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.iut.InfoFilm.model.Film;
import fr.iut.InfoFilm.model.Films;

public class FilmDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        Film film = null;
        Intent intent = getIntent();
        System.out.println(intent);
        if (intent != null){
            if (intent.hasExtra("film")){
                film = intent.getParcelableExtra("film");
                System.out.println(film.toString());
            }
        }
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