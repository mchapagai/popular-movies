package com.mchapagai.movies.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.PageLoader;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.MoviesGridAdapter;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.model.Movies;
import com.mchapagai.movies.model.binding.MovieResponse;
import com.mchapagai.movies.view_model.MovieViewModel;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

public class LandingFragment extends BaseFragment {

    private static final int COLUMN_COUNT = 2;
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
        final List<Movies> movieItems = response.getMovies();
        recyclerView.setAdapter(new MoviesGridAdapter(movieItems, null));
    }

    private void loadMovies() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(movieViewModel.discoverMovies("popularity.desc")
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(
                        this::movieResponseItems,
                        throwable -> MaterialDialogUtils.showDialog(getActivity(), R.string.service_error_title,
                                R.string.service_error_404, R.string.material_dialog_ok)
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
