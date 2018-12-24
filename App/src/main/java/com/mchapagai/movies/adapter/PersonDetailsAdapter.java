package com.mchapagai.movies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mchapagai.library.utils.AnimationUtils;
import com.mchapagai.library.utils.PaletteColorUtils;
import com.mchapagai.library.views.MaterialImageView;
import com.mchapagai.library.views.MaterialTextView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.binding.CombinedPersonResponse;
import com.mchapagai.movies.utils.MovieUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonDetailsAdapter extends RecyclerView.Adapter<PersonDetailsAdapter.ViewHolder> {

    private List<CombinedPersonResponse> combinedPersonItems;
    private Context context;
    private static int thumbnailWidth;

    public PersonDetailsAdapter(List<CombinedPersonResponse> combinedPersonItems, Context context) {
        this.combinedPersonItems = combinedPersonItems;
        this.context = context;

        int screenWidth = MovieUtils.calculateScreenWidth(context);
        int peekWidth = MovieUtils.convertDpToPixel(context, 32);
        thumbnailWidth = (screenWidth - peekWidth) / 3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.person_details_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final CombinedPersonResponse response = combinedPersonItems.get(position);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) viewHolder.profileImage.getLayoutParams();
        params.width = thumbnailWidth;
        viewHolder.profileImage.setLayoutParams(params);
        int calculateImageHeightRatio = 300 / 175;
        viewHolder.profileImage.setMaxHeight(calculateImageHeightRatio);

        AnimationUtils.resetTextColor(viewHolder.originalTitle);
        AnimationUtils.resetTextColor(viewHolder.subtitle);
        AnimationUtils.resetTextColor(viewHolder.caption);

        if (response.getPosterPath() != null && response.getOriginalTitle() != null && response.getCharacter() != null) {
            String imageEndpoint = Constants.SECURE_BASE_URL + response.getPosterPath();
            Picasso.get().load(imageEndpoint)
                    .resize(thumbnailWidth, calculateImageHeightRatio * thumbnailWidth)
                    .into(viewHolder.profileImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) viewHolder.profileImage.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(palette -> {
                                setUpInfoBackgroundColor(viewHolder.infoLayout, palette);
                                PaletteColorUtils.setupTextColors(viewHolder.originalTitle, palette);
                                PaletteColorUtils.setupTextColors(viewHolder.subtitle, palette);
                                PaletteColorUtils.setupTextColors(viewHolder.caption, palette);
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            // TODO Handle error in case of image call failure
                        }
                    });

            viewHolder.originalTitle.setText(response.getOriginalTitle());
            viewHolder.subtitle.setText(response.getCharacter());
            viewHolder.caption.setText(response.getReleaseDate());
        } else {
            viewHolder.infoLayout.setVisibility(View.GONE);
            viewHolder.profileImage.setVisibility(View.GONE);
            viewHolder.originalTitle.setVisibility(View.GONE);
            viewHolder.subtitle.setVisibility(View.GONE);
            viewHolder.caption.setVisibility(View.GONE);
        }
    }

    private void setUpInfoBackgroundColor(ConstraintLayout ll, Palette palette) {
        Palette.Swatch swatch = PaletteColorUtils.getMostPopulousSwatch(palette);
        if (swatch != null) {
            int startColor = ContextCompat.getColor(ll.getContext(), R.color.grey_800);
            int endColor = swatch.getRgb();

            AnimationUtils.animateBackgroundColorChange(ll, startColor, endColor);
        }
    }

    @Override
    public int getItemCount() {
        return combinedPersonItems == null ? 0 : combinedPersonItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialImageView profileImage;
        TextView originalTitle;
        MaterialTextView caption, subtitle;
        ConstraintLayout infoLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infoLayout = itemView.findViewById(R.id.credit_profile_info);
            originalTitle = itemView.findViewById(R.id.credit_profile_title);
            subtitle = itemView.findViewById(R.id.credit_profile_subtitle);
            caption = itemView.findViewById(R.id.credit_profile_caption);
            profileImage = itemView.findViewById(R.id.credit_profile_image);
        }
    }
}
