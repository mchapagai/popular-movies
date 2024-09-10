package com.mchapagai.movies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.core.response.people.CrewCreditResponse;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.utils.AnimationUtils;
import com.mchapagai.movies.utils.MovieUtils;
import com.mchapagai.movies.utils.PaletteColorUtils;
import com.mchapagai.movies.views.MaterialTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> {

    private List<CrewCreditResponse> crewItems;
    private static int thumbnailWidth;

    public CrewAdapter(List<CrewCreditResponse> crewItems, Context context) {
        this.crewItems = crewItems;
        int screenWidth = MovieUtils.calculateScreenWidth(context);
        int width = MovieUtils.convertDpToPixel(context, 32);
        thumbnailWidth = (screenWidth - width) / 3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_details_item, viewGroup, false);
        return new CrewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final CrewCreditResponse response = crewItems.get(position);

        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) viewHolder.profileImage
                        .getLayoutParams();
        params.width = thumbnailWidth;
        viewHolder.profileImage.setLayoutParams(params);
        int calculateImageHeightRatio = 300 / 175;
        viewHolder.profileImage.setMaxHeight(calculateImageHeightRatio);

        AnimationUtils.resetTextColor(viewHolder.originalTitle);
        AnimationUtils.resetTextColor(viewHolder.subtitle);
        AnimationUtils.resetTextColor(viewHolder.caption);

        String imageEndpoint = Constants.SECURE_BASE_URL + response.getPosterPath();
        if (!TextUtils.isEmpty(response.getPosterPath())) {
            Picasso.get().load(imageEndpoint)
                    .resize(thumbnailWidth, calculateImageHeightRatio * thumbnailWidth)
                    .placeholder(R.drawable.graphics_gradient_background)
                    .into(viewHolder.profileImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap =
                                    ((BitmapDrawable) viewHolder.profileImage.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(palette -> {
                                setUpInfoBackgroundColor(viewHolder.infoLayout, palette);
                                PaletteColorUtils.setupTextColors(viewHolder.originalTitle,
                                        palette);
                                PaletteColorUtils.setupTextColors(viewHolder.subtitle, palette);
                                PaletteColorUtils.setupTextColors(viewHolder.caption, palette);
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            // TODO Handle error in case of image call failure
                        }
                    });
        } else {
            viewHolder.infoLayout.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(response.getOriginalTitle())) {
            viewHolder.originalTitle.setText(response.getOriginalTitle());
        }
        if (!TextUtils.isEmpty(response.getJob())) {
            viewHolder.subtitle.setText(response.getJob());
        }
        if (!TextUtils.isEmpty(response.getReleaseDate())) {
            viewHolder.caption.setText(response.getReleaseDate());
        }
    }

    private void setUpInfoBackgroundColor(ConstraintLayout ll, Palette palette) {
        Palette.Swatch swatch = PaletteColorUtils.getMostPopulousSwatch(palette);
        if (swatch != null) {
            int startColor = ContextCompat.getColor(ll.getContext(), R.color.animate_background);
            int endColor = swatch.getRgb();
            AnimationUtils.animateBackgroundColorChange(ll, startColor, endColor);
        }
    }

    @Override
    public int getItemCount() {
        return crewItems == null ? 0 : crewItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        MaterialTextView originalTitle;
        MaterialTextView subtitle;
        MaterialTextView caption;
        ConstraintLayout infoLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.credit_profile_image);
            originalTitle = itemView.findViewById(R.id.credit_profile_title);
            subtitle = itemView.findViewById(R.id.credit_profile_subtitle);
            caption = itemView.findViewById(R.id.credit_profile_caption);
            infoLayout = itemView.findViewById(R.id.credit_details_layout);
        }
    }
}

