package com.example.mchapagai.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.utils.MaterialDialogUtils;
import com.example.library.views.PageLoader;
import com.example.mchapagai.R;
import com.example.mchapagai.activity.LandingActivity;
import com.example.mchapagai.adapter.MoviesGridAdapter;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.BaseFragment;
import com.example.mchapagai.model.Movies;
import com.example.mchapagai.model.binding.MovieResponse;
import com.example.mchapagai.view_model.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class LandingFragment extends BaseFragment {

    private static final String TAG = LandingFragment.class.getSimpleName();

    private static final int COLUMN_COUNT = 2;
    private List<Movies> movieItems = new ArrayList<>();
    private MoviesGridAdapter moviesGridAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recyclerView;
    private PageLoader pageLoader;

    @Inject
    MovieViewModel movieViewModel;

    public LandingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.landing_fragment_layout, container, false);

        recyclerView = view.findViewById(R.id.movies_recycler_view);
        pageLoader = view.findViewById(R.id.movies_page_loader);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), COLUMN_COUNT);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void movieResponseItems(MovieResponse response) {
        movieItems = response.getMovies();
        recyclerView.setAdapter(new MoviesGridAdapter(movieItems));
    }

    private void loadMovies() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(movieViewModel.discoverMovies()
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(
                        new Consumer<MovieResponse>() {
                            @Override
                            public void accept(MovieResponse response) {
                                movieResponseItems(response);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                MaterialDialogUtils.showDialog(getActivity(), R.string.service_error_title,
                                        R.string.service_error_404, R.string.material_dialog_ok);
                            }
                        }
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
