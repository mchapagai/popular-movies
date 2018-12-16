package com.example.mchapagai.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.library.views.MaterialCircleImageView;
import com.example.mchapagai.R;
import com.example.mchapagai.common.Constants;
import com.example.mchapagai.model.CastCredit;
import com.example.mchapagai.model.CrewCredits;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.ViewHolder> {

    private String creditType;
    private List<CastCredit> castCreditItems;
    private List<CrewCredits> crewCreditItems;

    private Boolean isCreditTypeCast() {
        if (creditType.equalsIgnoreCase("cast")) {
            return true;
        }
        return false;
    }

    public CreditsAdapter(List<CastCredit> castCreditItems, List<CrewCredits> crewCreditItems, String creditType) {
        this.castCreditItems = castCreditItems;
        this.crewCreditItems = crewCreditItems;
        this.creditType = creditType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details_credit_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (isCreditTypeCast()) {
            final CastCredit cast = castCreditItems.get(position);
            holder.textName.setText(cast.getName());
            holder.textInfo.setText(cast.getCharacter());
            Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + cast.getProfilePath())
                    .into(holder.profileImage);
        } else {
            final CrewCredits cast = crewCreditItems.get(position);
            holder.textName.setText(cast.getName());
            holder.textInfo.setText(cast.getJob());
            Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + cast.getProfilePath())
                    .into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        if (castCreditItems != null && isCreditTypeCast()) {
            return castCreditItems.size();
        }
        return crewCreditItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textInfo;
        MaterialCircleImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.txt_name);
            textInfo = itemView.findViewById(R.id.txt_info);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}