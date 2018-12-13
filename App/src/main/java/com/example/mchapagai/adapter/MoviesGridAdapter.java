package com.example.mchapagai.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.library.views.MaterialImageView;
import com.example.mchapagai.R;
import com.example.mchapagai.common.Constants;
import com.example.mchapagai.model.Movies;
import com.example.mchapagai.utils.MovieUtils;
import com.example.mchapagai.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesGridAdapter extends RecyclerView.Adapter<MoviesGridAdapter.MoviesViewHolder> {

    private List<Movies> movieItems;
    private OnItemClickListener onItemClickListener;

    public MoviesGridAdapter(List<Movies> movieItems, OnItemClickListener onItemClickListener) {
        this.movieItems = movieItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_poster_grid_items, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {
        final Movies movies = movieItems.get(position);

        Uri posterUrl = MovieUtils.getMoviePosterPathUri(movies);

        Picasso.get()
                .load(posterUrl)
                .transform(new RoundedTransformation(20, 0))
                .into(holder.poster);

        holder.poster.setOnClickListener(v -> onItemClickListener.onClickItem(movies, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        MaterialImageView poster;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster);
        }
    }

    public interface OnItemClickListener {
        void onClickItem(Movies movies, int position);
    }

}