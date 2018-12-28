package com.mchapagai.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mchapagai.library.views.MaterialCircleImageView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.binding.CombinedCreditsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.ViewHolder> {

    private List<CombinedCreditsResponse> combinedCreditsResponseList;
    private OnPersonClickListener onItemClickListener;

    public CreditsAdapter(List<CombinedCreditsResponse> combinedCreditsResponseList) {
        this.combinedCreditsResponseList = combinedCreditsResponseList;
    }

    public void setOnItemClickListener(OnPersonClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details_credit_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CombinedCreditsResponse combinedCreditsResponse = combinedCreditsResponseList.get(position);

        holder.textName.setText(combinedCreditsResponse.getName());
        holder.textInfo.setText(combinedCreditsResponse.getDescription());
        Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + combinedCreditsResponse.getProfileImagePath()).into(holder.profileImage);

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(combinedCreditsResponse));
    }

    @Override
    public int getItemCount() {
        return combinedCreditsResponseList == null ? 0 : combinedCreditsResponseList.size();

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
        void onItemClick(CombinedCreditsResponse combinedCreditsResponse);
    }

}