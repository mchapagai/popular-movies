package com.mchapagai.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.movies.views.MaterialTagView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.model.Genres;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {

    private List<Genres> genreItems;

    public GenresAdapter(List<Genres> genreItems) {
        this.genreItems = genreItems;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_details_genre_items, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        final Genres movies = genreItems.get(position);
        holder.genreTextView.setUseRandomColor(true);
        holder.genreTextView.setTagText(movies.getName());
    }

    @Override
    public int getItemCount() {
        return genreItems == null ? 0 : genreItems.size();
    }

    class GenreViewHolder extends RecyclerView.ViewHolder {
        MaterialTagView genreTextView;

        GenreViewHolder(View itemView) {
            super(itemView);
            genreTextView = itemView.findViewById(R.id.genre_text);
        }
    }
}