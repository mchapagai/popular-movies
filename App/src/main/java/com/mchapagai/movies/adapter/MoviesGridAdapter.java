package com.mchapagai.movies.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.mchapagai.library.views.MaterialImageView;
import com.mchapagai.library.widget.RoundedTransformation;
import com.mchapagai.movies.R;
import com.mchapagai.movies.model.Movies;
import com.mchapagai.movies.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesGridAdapter extends
        RecyclerView.Adapter<MoviesGridAdapter.MoviesViewHolder> implements
        Filterable {

    private List<Movies> movieItems;
    private List<Movies> movieItemsFilterable;
    private OnItemClickListener onItemClickListener;

    public MoviesGridAdapter(List<Movies> movieItems) {
        this.movieItems = movieItems;
        this.movieItemsFilterable = movieItems;
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     *
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                String sequence = constraint.toString();
                if (sequence.isEmpty()) {
                    movieItemsFilterable = movieItems;
                } else {
                    List<Movies> filteredList = new ArrayList<>();
                    filteredList.addAll(movieItems);
                    movieItemsFilterable = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = movieItemsFilterable;
                return results;
            }

            @Override
            protected void publishResults(final CharSequence constraint,
                    final FilterResults results) {
                movieItemsFilterable = (List<Movies>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
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

        ViewCompat.setTransitionName(holder.poster,
                String.valueOf(R.string.movies_poster_transition));

        holder.poster.setOnClickListener(
                v -> onItemClickListener.onClickItem(movies, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movieItemsFilterable == null ? 0 : movieItemsFilterable.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster)
        MaterialImageView poster;

        MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void notifyDataChange(List<Movies> items) {
        movieItemsFilterable.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClickItem(Movies movies, int position);
    }

}