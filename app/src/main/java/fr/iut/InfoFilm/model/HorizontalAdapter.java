package fr.iut.InfoFilm.model;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.iut.InfoFilm.FilmDetail;
import fr.iut.InfoFilm.R;
import fr.iut.InfoFilm.SearchByKeyword;
import fr.iut.InfoFilm.ShowResult;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private ArrayList<Film> films;



    public HorizontalAdapter(ArrayList<Film> items) {

        this.films = items;

    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.simple_vue_movie, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("le titre du film touché : ");
            for(Film f : films){
                if(f.getName().equals(((TextView) view.findViewById(R.id.title_movie)).getText().toString())){
                    Intent t = new Intent(parent.getContext(), FilmDetail.class);
                    t.putExtra("film",f);
                    parent.getContext().startActivity(t);
                }

                }
            }
        });
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {

        ImageView img = holder.itemView.findViewById(R.id.imgMovie);
        TextView txt = holder.itemView.findViewById(R.id.title_movie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try{
                if(films.get(position).geturl() == null){
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1625457671853-5745024cf1cf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=478&q=80")
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(img);

                }else{
                    Picasso.get()
                            .load(films.get(position).geturl())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(img);
                }
            }catch (NullPointerException e){
                Picasso.get()
                        .load("https://images.unsplash.com/photo-1625457671853-5745024cf1cf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=478&q=80")
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(img);
            }
        }
        try{
            txt.setText(films.get(position).getName());
        }catch (NullPointerException e){
            txt.setText("/!\\ No Title Found /!\\");
        }
    }


    @Override
    public int getItemCount() {
        return films.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            ImageView imgMovie = (ImageView) itemView.findViewById(R.id.imgMovie);
            TextView title = (TextView) itemView.findViewById(R.id.title_movie);
        } }
}

