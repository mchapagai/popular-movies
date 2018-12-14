package com.example.mchapagai.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mchapagai.R;
import com.example.mchapagai.model.Genres;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {

    private List<Genres> genreItems;

    public GenresAdapter(List<Genres> genreItems) {
        this.genreItems = genreItems;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details_genre_items, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        final Genres movies = genreItems.get(position);
        holder.genreTextView.setText(movies.getName());
    }

    @Override
    public int getItemCount() {
        if (genreItems != null)
            return genreItems.size();
        else
            return 0;
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreTextView;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreTextView = itemView.findViewById(R.id.details_genre);
        }
    }
}