package com.mchapagai.movies.adapter.movies;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mchapagai.library.views.MaterialImageView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.model.movies.Movies;
import com.mchapagai.movies.utils.MovieUtils;
import com.mchapagai.library.widget.RoundedTransformation;
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

        ViewCompat.setTransitionName(holder.poster, String.valueOf(R.string.movies_poster_transition));

        holder.poster.setOnClickListener(v -> onItemClickListener.onClickItem(movies, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster) MaterialImageView poster;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClickItem(Movies movies, int position);
    }

}