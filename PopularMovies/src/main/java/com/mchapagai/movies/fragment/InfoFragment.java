package com.mchapagai.movies.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.OnTheAir;
import com.mchapagai.movies.utils.DateTimeUtils;
import com.mchapagai.movies.views.MaterialTextView;

import java.util.Locale;


public class InfoFragment extends BaseFragment {

    MaterialTextView showsEtailsOverView;

    MaterialTextView showsRatings;

    MaterialTextView showsReleaseDate;

    private OnTheAir onTheAir;

    public static InfoFragment newInstance(OnTheAir onTheAir) {
        if (onTheAir == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable(Constants.SHOWS_DETAILS, onTheAir);

        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_details_info_fragment_container, container,
                false);

        showsEtailsOverView = view.findViewById(R.id.shows_etails_over_view);
        showsRatings = view.findViewById(R.id.shows_ratings);
        showsReleaseDate = view.findViewById(R.id.shows_release_date);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onTheAir = getArguments().getParcelable(Constants.SHOWS_DETAILS);

        showsReleaseDate.setText(DateTimeUtils.getNameOfMonth(onTheAir.getFirstAirDate()));

        showsRatings.setText(getString(R.string.details_rating_votes_count,
                String.format(Locale.US, "%.2f", onTheAir.getVoteAverage()),
                String.valueOf(onTheAir.getVoteCount())));
        showsEtailsOverView.setText(onTheAir.getOverview());
    }

}
