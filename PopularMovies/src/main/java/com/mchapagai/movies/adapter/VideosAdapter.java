package com.mchapagai.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.core.response.common.VideoResponse;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.views.MaterialTextView;
import com.mchapagai.movies.widget.OnItemClickListener;
import com.mchapagai.movies.widget.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {

    private List<VideoResponse> videoItems;
    private OnItemClickListener onItemClick;

    public VideosAdapter(Context context, List<VideoResponse> videoItems) {
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_details_video_items, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoViewHolder holder, int position) {
        final VideoResponse videos = videoItems.get(position);

        if (videos.isYoutubeVideo()) {
            Picasso.get()
                    .load(String.format(Constants.YOUTUBE_THUMBNAIL, videos.getKey()))
                    .transform(new RoundedTransformation(14, 0))
                    .into(holder.videoThumbnail);
        }
        holder.videoThumbnailTitle.setText(videos.getName());
    }

    @Override
    public int getItemCount() {
        return videoItems == null ? 0 : videoItems.size();
    }

    public void setOnItemClick(OnItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView videoThumbnail;
        MaterialTextView videoThumbnailTitle;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.movie_video_thumbnail);
            videoThumbnailTitle = itemView.findViewById(R.id.movie_video_thumbnail_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClick.onItemClick(view, getAdapterPosition());
        }
    }

    public void setMovieVideos(List<VideoResponse> items) {
        videoItems.clear();
        videoItems = items;
        notifyDataSetChanged();
    }

    public VideoResponse getItem(int position) {
        if (videoItems == null || position < 0 || position > videoItems.size()) {
            return null;
        }
        return videoItems.get(position);
    }
}
