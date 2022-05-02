package fr.iut.InfoFilm.model;

import static java.util.Objects.isNull;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.iut.InfoFilm.R;

public class FilmAdapter extends ArrayAdapter<Film> {
    public FilmAdapter(Context context, ArrayList<Film> films) {
        super(context, 0, films);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Film film = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_vue_movie, parent, false);
        }
        // Lookup view for data population
        ImageView imgMovie = (ImageView) convertView.findViewById(R.id.imgMovie);
        TextView title = (TextView) convertView.findViewById(R.id.title_movie);
        // Populate the data into the template view using the data object

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try{
                if(film.geturl() == null){
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1625457671853-5745024cf1cf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=478&q=80")
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(imgMovie);

                }else{
                    Picasso.get()
                            .load(film.geturl())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(imgMovie);
                }
            }catch (NullPointerException e){
                Picasso.get()
                        .load("https://images.unsplash.com/photo-1625457671853-5745024cf1cf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=478&q=80")
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imgMovie);
            }
        }
        try{
            title.setText(film.getName());
        }catch (NullPointerException e){
            title.setText("/!\\ No Title Found /!\\");
        }

        return convertView;
    }
}
