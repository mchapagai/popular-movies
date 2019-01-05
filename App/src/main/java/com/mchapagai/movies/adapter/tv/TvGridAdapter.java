package com.mchapagai.movies.adapter.tv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mchapagai.library.views.MaterialImageView;
import com.mchapagai.library.widget.RoundedTransformation;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.tv.OnTheAir;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TvGridAdapter extends RecyclerView.Adapter<TvGridAdapter.ViewHolder> {

    private List<OnTheAir> onTheAirItems;

    public TvGridAdapter(List<OnTheAir> onTheAirItems) {
        this.onTheAirItems = onTheAirItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_poster_grid_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final OnTheAir shows = onTheAirItems.get(position);

        String posterUrl = Constants.SECURE_BASE_URL + shows.getPosterPath();
        Picasso.get()
                .load(posterUrl)
                .transform(new RoundedTransformation(20, 0))
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return onTheAirItems == null ? 0 : onTheAirItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster)
        MaterialImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
