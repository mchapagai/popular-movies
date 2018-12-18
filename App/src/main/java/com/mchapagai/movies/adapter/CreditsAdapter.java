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
import com.mchapagai.movies.model.CastCredit;
import com.mchapagai.movies.model.CrewCredits;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.ViewHolder> {

    private List<CastCredit> castCreditItems;
    private List<CrewCredits> crewCreditItems;
    private OnPersonClickListener onItemClickListener;

    public CreditsAdapter(List<CastCredit> castCreditItems, List<CrewCredits> crewCreditItems) {
        this.castCreditItems = castCreditItems;
        this.crewCreditItems = crewCreditItems;
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

        holder.itemView.setTag(castCreditItems);
        holder.itemView.setTag(crewCreditItems);

        final CastCredit cast = castCreditItems.get(position);
        holder.textName.setText(cast.getName());
        holder.textInfo.setText(cast.getCharacter());
        Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + cast.getProfilePath())
                .into(holder.profileImage);
        final CrewCredits crew = crewCreditItems.get(position);
        holder.textName.setText(crew.getName());
        holder.textInfo.setText(crew.getJob());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(cast));
    }

    @Override
    public int getItemCount() {
        if (castCreditItems != null) {
            return castCreditItems.size();
        }
        return crewCreditItems == null ? 0 : crewCreditItems.size();
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
        void onItemClick(CastCredit crewCredits);
    }

}