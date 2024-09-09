package com.mchapagai.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.core.response.common.ReviewResponse;
import com.mchapagai.movies.R;
import com.mchapagai.movies.views.ExpandableTextView;
import com.mchapagai.movies.views.MaterialTextView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private final List<ReviewResponse> reviewItems;

    public ReviewsAdapter(List<ReviewResponse> reviewItems) {
        this.reviewItems = reviewItems;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_reviews_list_item, viewGroup, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        final ReviewResponse reviews = reviewItems.get(position);

        holder.reviewAuthor.setText(reviews.getAuthor());
        holder.reviewContent.setInterpolator(new OvershootInterpolator());
        holder.reviewContent.setText(reviews.getContent());
        holder.reviewContent.setMaxLines(4);
        holder.reviewContent.setEllipsize(null);
        holder.reviewIcon.setOnClickListener(view -> {
            holder.reviewIcon.setImageResource(
                    holder.reviewContent.isExpanded() ? R.drawable.graphics_chevron_down
                            : R.drawable.graphics_chevron_up);
            holder.reviewContent.toggle();
        });
        holder.reviewsParentLayout.setOnClickListener(view -> {
            holder.reviewIcon.setImageResource(
                    holder.reviewContent.isExpanded() ? R.drawable.graphics_chevron_down
                            : R.drawable.graphics_chevron_up);
            holder.reviewContent.toggle();
        });
    }

    @Override
    public int getItemCount() {
        return reviewItems == null ? 0 : reviewItems.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView reviewAuthor;
        ExpandableTextView reviewContent;
        ImageView reviewIcon;
        ConstraintLayout reviewsParentLayout;

        ReviewsViewHolder(View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.review_author_name);
            reviewContent = itemView.findViewById(R.id.review_content);
            reviewIcon = itemView.findViewById(R.id.review_icon);
            reviewsParentLayout = itemView.findViewById(R.id.review_constraint_layout);
        }
    }
}