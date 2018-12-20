package com.mchapagai.movies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mchapagai.library.views.MaterialCircleImageView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.CombinedCredits;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.ViewHolder> {

    private List<CombinedCredits> combinedCreditsList;
    private OnPersonClickListener onItemClickListener;

    public CreditsAdapter(List<CombinedCredits> combinedCreditsList) {
        this.combinedCreditsList = combinedCreditsList;
    }

    public void setOnItemClickListener(OnPersonClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details_credit_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CombinedCredits combinedCredits = combinedCreditsList.get(position);

        holder.textName.setText(combinedCredits.getName());
        holder.textInfo.setText(combinedCredits.getDescription());
        Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + combinedCredits.getProfileImagePath()).into(holder.profileImage);

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(combinedCredits));
    }

    @Override
    public int getItemCount() {
        return combinedCreditsList == null ? 0 : combinedCreditsList.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textInfo;
        MaterialCircleImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.credit_name);
            textInfo = itemView.findViewById(R.id.credit_info);
            profileImage = itemView.findViewById(R.id.credit_profile_image);
        }

    }

    public interface OnPersonClickListener {
        void onItemClick(CombinedCredits combinedCredits);
    }

}