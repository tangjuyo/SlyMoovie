package fr.iut.InfoFilm.model;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.iut.InfoFilm.FilmDetail;
import fr.iut.InfoFilm.R;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder>{


    private ArrayList<Film> films;



    public TimeLineAdapter(ArrayList<Film> items) {
        this.films = items;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.timeline, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Film f : films){
                    if(f.getName().equals(((TextView) view.findViewById(R.id.item_title)).getText().toString())){
                        Intent t = new Intent(parent.getContext(), FilmDetail.class);
                        HistoSingleton.addToSingleton(f);
                        t.putExtra("film",f);
                        parent.getContext().startActivity(t);
                    }
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {
        TextView title = holder.itemView.findViewById(R.id.item_title);
        RatingBar note = holder.itemView.findViewById(R.id.note);
        note.setRating((films.get(position).getNotes()/2));
        title.setText(films.get(position).getName());
        //load l'image sur la timeline
        ImageView img = holder.itemView.findViewById(R.id.imgVue);
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
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            TextView mItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            RatingBar mItemSubtitle = (RatingBar) itemView.findViewById(R.id.note);
            RelativeLayout mItemLine = (RelativeLayout) itemView.findViewById(R.id.item_line);
        }
    }

    @Override
    public int getItemCount() {
        return films.size();
    }
}
