package com.mchapagai.movies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.PageLoader;
import com.mchapagai.movies.R;
import com.mchapagai.movies.activity.MovieDetailsActivity;
import com.mchapagai.movies.adapter.movies.MoviesGridAdapter;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.Sort;
import com.mchapagai.movies.model.movies.Movies;
import com.mchapagai.movies.model.movies.binding.MovieResponse;
import com.mchapagai.movies.view_model.MovieViewModel;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

public class DiscoverMoviesFragment extends BaseFragment implements MoviesGridAdapter.OnItemClickListener {

    private static final int COLUMN_COUNT = 2;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Sort sort = Sort.MOST_POPULAR;

    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.movies_page_loader)
    PageLoader pageLoader;

    @Inject
    MovieViewModel movieViewModel;

    public DiscoverMoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_movies_fragment_container, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClickItem(final Movies movies, final int position) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        setSharedElementEnterTransition(
                new TransitionSet().addTransition(new ChangeBounds()).addTransition(new ChangeImageTransform())
                        .addTransition(new ChangeTransform()));
        setEnterTransition(new Fade());
        setExitTransition(new Fade());
        setSharedElementReturnTransition(
                new TransitionSet().addTransition(new ChangeBounds()).addTransition(new ChangeImageTransform())
                        .addTransition(new ChangeTransform()));
        startActivity(intent.putExtra(Constants.MOVIE_DETAILS, movies));
    }

    private void movieResponseItems(MovieResponse response) {
        final List<Movies> movieItems = response.getMovies();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), COLUMN_COUNT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MoviesGridAdapter(movieItems, this));
    }

    private void loadMovies() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(movieViewModel.discoverMovies(sort.toString())
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
        compositeDisposable.dispose();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.movies_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void onSortChanged(Sort sort) {
        this.sort = sort;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menu_sort_popularity:
                item.setChecked(!item.isChecked());
                onSortChanged(Sort.MOST_POPULAR);
                loadMovies();
                break;
            case R.id.menu_sort_vote_count:
                item.setChecked(!item.isChecked());
                onSortChanged(Sort.MOST_RATED);
                loadMovies();
                break;
            case R.id.menu_sort_vote_average:
                item.setChecked(!item.isChecked());
                onSortChanged(Sort.TOP_RATED);
                loadMovies();
                break;
        }
        getActivity().invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

}
