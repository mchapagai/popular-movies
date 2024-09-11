package com.mchapagai.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.core.response.shows.ShowResponse;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.OnTheAir;
import com.mchapagai.movies.widget.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowsGridAdapter extends RecyclerView.Adapter<ShowsGridAdapter.ViewHolder> {

    private List<ShowResponse> onTheAirItems;
    private OnShowClickListener onShowClickListener;

    public ShowsGridAdapter(List<ShowResponse> onTheAirItems) {
        this.onTheAirItems = onTheAirItems;
    }

    public void setOnShowClickListener(final OnShowClickListener onShowClickListener) {
        this.onShowClickListener = onShowClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_poster_grid_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(final int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ShowResponse shows = onTheAirItems.get(position);

        String posterUrl = Constants.SECURE_BASE_URL + shows.getPosterPath();
        Picasso.get().load(posterUrl)
                .transform(new RoundedTransformation(20, 0))
                .into(holder.poster);
        holder.poster.setOnClickListener(
                v -> onShowClickListener.onItemClick(shows, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return onTheAirItems == null ? 0 : onTheAirItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster);
        }
    }

    public interface OnShowClickListener {
        void onItemClick(ShowResponse onTheAir, int position);
    }

}
