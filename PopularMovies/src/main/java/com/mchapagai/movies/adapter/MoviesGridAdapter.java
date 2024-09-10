package com.mchapagai.movies.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.core.response.movies.MovieResponse;
import com.mchapagai.movies.widget.RoundedTransformation;
import com.mchapagai.movies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesGridAdapter extends
        RecyclerView.Adapter<MoviesGridAdapter.MoviesViewHolder> implements
        Filterable {

    private final List<MovieResponse> movieItems;
    private List<MovieResponse> movieItemsFilterable;
    private OnItemClickListener onItemClickListener;

    public MoviesGridAdapter(List<MovieResponse> movieItems) {
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
                    List<MovieResponse> filteredList = new ArrayList<>();
                    filteredList.addAll(movieItems);
                    movieItemsFilterable = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = movieItemsFilterable;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(final CharSequence constraint,
                    final FilterResults results) {
                //noinspection unchecked
                movieItemsFilterable = (List<MovieResponse>) results.values;
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
        final MovieResponse movies = movieItems.get(position);

        Uri posterUrl = Uri.parse(movies.getFullPosterPath());
        Picasso.get()
                .load(posterUrl)
                .transform(new RoundedTransformation(20, 0))
                .into(holder.poster);

        ViewCompat.setTransitionName(holder.poster,
                String.valueOf(R.string.movies_poster_transition));

        holder.poster.setOnClickListener(
                v -> onItemClickListener.onClickItem(movies.getId(), holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movieItemsFilterable == null ? 0 : movieItemsFilterable.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        MoviesViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster);
        }
    }

    public void notifyDataChange(List<MovieResponse> items) {
        movieItemsFilterable.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClickItem(int movidId, int position);
    }

}