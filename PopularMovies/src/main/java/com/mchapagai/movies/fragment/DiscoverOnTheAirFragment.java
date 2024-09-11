package com.mchapagai.movies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.movies.R;
import com.mchapagai.movies.activity.ShowDetailsActivity;
import com.mchapagai.movies.adapter.ShowsGridAdapter;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.OnTheAir;
import com.mchapagai.movies.model.binding.OnTheAirResponse;
import com.mchapagai.movies.utils.MaterialDialogUtils;
import com.mchapagai.movies.view_model.ShowsViewModel;
import com.mchapagai.movies.views.PageLoader;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DiscoverOnTheAirFragment extends BaseFragment {

    private static final int COLUMN_COUNT = 2;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    RecyclerView recyclerView;

    PageLoader pageLoader;

    @Inject
    ShowsViewModel showsViewModel;

    public DiscoverOnTheAirFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_fragment_container, container, false);

        recyclerView = view.findViewById(R.id.common_recycler_view);
        pageLoader = view.findViewById(R.id.common_page_loader);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void showsResponseItems(OnTheAirResponse response) {
        final List<OnTheAir> ontheAirItems = response.getOnTheAir();
        ShowsGridAdapter tvGridAdapter = new ShowsGridAdapter(ontheAirItems);
        tvGridAdapter.setOnShowClickListener((shows, position) -> {
            Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
            intent.putExtra(Constants.SHOWS_DETAILS, shows);
            startActivity(intent);
        });
        recyclerView.setAdapter(tvGridAdapter);
    }

    private void loadMovies() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(showsViewModel.discoverOnTheAirShows()
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(
                        response -> showsResponseItems(response),
                        throwable -> MaterialDialogUtils.showDialog(getActivity(),
                                getResources().getString(R.string.service_error_title),
                                throwable.getMessage(),
                                getResources().getString(R.string.material_dialog_ok))
                ));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMovies();
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }
}
