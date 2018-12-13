package com.example.mchapagai.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mchapagai.R;
import com.example.mchapagai.model.Reviews;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<Reviews> reviewItems;

    public ReviewsAdapter(List<Reviews> reviewItems) {
        this.reviewItems = reviewItems;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_reviews_list_item, viewGroup, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        final Reviews reviews = reviewItems.get(position);

        holder.contentView.setText(reviews.getReviewContent());
        holder.contentView.setMaxLines(5);
        holder.contentView.setEllipsize(null);
        holder.authorView.setText(reviews.getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {

        public final View holderView;
        TextView authorView;
        TextView contentView;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            holderView = itemView;
            authorView = holderView.findViewById(R.id.review_author);
            contentView = holderView.findViewById(R.id.review_content);
        }
    }
}