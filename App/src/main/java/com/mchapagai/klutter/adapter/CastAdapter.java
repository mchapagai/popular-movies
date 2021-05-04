package com.mchapagai.klutter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.klutter.utils.AnimationUtils;
import com.mchapagai.klutter.utils.PaletteColorUtils;
import com.mchapagai.klutter.views.MaterialImageView;
import com.mchapagai.klutter.views.MaterialTextView;
import com.mchapagai.klutter.R;
import com.mchapagai.klutter.common.Constants;
import com.mchapagai.klutter.model.CombinedCastCredit;
import com.mchapagai.klutter.utils.MovieUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<CombinedCastCredit> castItems;
    private static int thumbnailWidth;

    public CastAdapter(List<CombinedCastCredit> castItems, Context context) {
        this.castItems = castItems;
        int screenWidth = MovieUtils.calculateScreenWidth(context);
        int width = MovieUtils.convertDpToPixel(context, 32);
        thumbnailWidth = (screenWidth - width) / 3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_details_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final CombinedCastCredit response = castItems.get(position);

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
        if (!TextUtils.isEmpty(response.getCharacter())) {
            viewHolder.subtitle.setText(response.getCharacter());
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
        return castItems == null ? 0 : castItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.credit_profile_image)
        MaterialImageView profileImage;
        @BindView(R.id.credit_profile_title)
        MaterialTextView originalTitle;
        @BindView(R.id.credit_profile_subtitle)
        MaterialTextView subtitle;
        @BindView(R.id.credit_profile_caption)
        MaterialTextView caption;
        @BindView(R.id.credit_details_layout)
        ConstraintLayout infoLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
