package com.mchapagai.klutter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.klutter.R;
import com.mchapagai.klutter.model.Reviews;
import com.mchapagai.klutter.views.ExpandableTextView;
import com.mchapagai.klutter.views.MaterialImageView;
import com.mchapagai.klutter.views.MaterialTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<Reviews> reviewItems;

    public ReviewsAdapter(List<Reviews> reviewItems) {
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
        final Reviews reviews = reviewItems.get(position);

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

        @BindView(R.id.review_author_name)
        MaterialTextView reviewAuthor;
        @BindView(R.id.review_content)
        ExpandableTextView reviewContent;
        @BindView(R.id.review_icon)
        MaterialImageView reviewIcon;
        @BindView(R.id.review_constraint_layout)
        ConstraintLayout reviewsParentLayout;

        ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}