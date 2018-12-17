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
import com.example.mchapagai.model.binding.PersonResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonDetailsAdapter extends RecyclerView.Adapter<PersonDetailsAdapter.ViewHolder> {

    private List<PersonResponse> combinedCastsItems;

    public PersonDetailsAdapter(List<PersonResponse> combinedCastsItems) {
        this.combinedCastsItems = combinedCastsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.person_details_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final PersonResponse response = combinedCastsItems.get(position);

        viewHolder.personDetails.setText(response.getName());
        viewHolder.personBio.setText(response.getBiography());
            Picasso.get().load(Constants.MOVIE_POSTER_ENDPOINT + response.getProfilePath())
                    .into(viewHolder.profileImage);
    }

    @Override
    public int getItemCount() {
        return combinedCastsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCircleImageView profileImage;
        TextView personBio, personDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.credit_profile_image);
            personBio = itemView.findViewById(R.id.credit_name);
            personDetails = itemView.findViewById(R.id.credit_info);
        }
    }
}
