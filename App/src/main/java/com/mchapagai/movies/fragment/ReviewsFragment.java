package com.mchapagai.movies.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.ReviewsAdapter;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.OnTheAir;
import com.mchapagai.movies.model.Reviews;
import com.mchapagai.movies.model.binding.ReviewsResponse;
import com.mchapagai.movies.view_model.ShowsViewModel;

import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

import javax.inject.Inject;

public class ReviewsFragment extends BaseFragment {

    @BindView(R.id.show_detail_review_header)
    TextView detailReviewHeader;

    @BindView(R.id.show_reviews_recycler_view)
    RecyclerView reviewsRecyclerView;

    @Inject
    ShowsViewModel showsViewModel;

    private OnTheAir onTheAir;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static ReviewsFragment newInstance(OnTheAir onTheAir) {
        if (onTheAir == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable(Constants.SHOWS_DETAILS, onTheAir);

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onTheAir = getArguments().getParcelable(Constants.SHOWS_DETAILS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_details_reviews_fragment_container, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void loadMovieReviews() {
        compositeDisposable.add(showsViewModel.getReviewsById(onTheAir.getId())
                .subscribe(
                        response -> reviewsResponseItems(response),
                        throwable -> MaterialDialogUtils.showDialog(getActivity(),
                                getResources().getString(R.string.service_error_title),
                                throwable.getMessage(),
                                getResources().getString(R.string.material_dialog_ok))
                ));
    }

    private void reviewsResponseItems(ReviewsResponse response) {
        List<Reviews> reviewItems = response.getReviews();
        if (reviewItems.isEmpty()) {
            reviewsRecyclerView.setVisibility(View.GONE);
            detailReviewHeader.setVisibility(View.GONE);
        } else {
            reviewsRecyclerView.setVisibility(View.VISIBLE);
            detailReviewHeader.setVisibility(View.VISIBLE);
        }
        reviewsRecyclerView.setAdapter(new ReviewsAdapter(reviewItems));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMovieReviews();
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

}
