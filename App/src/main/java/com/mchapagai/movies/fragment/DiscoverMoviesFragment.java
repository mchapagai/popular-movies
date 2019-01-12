package com.mchapagai.movies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeTransform;
import androidx.transition.Fade;
import androidx.transition.TransitionSet;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mchapagai.library.views.PageLoader;
import com.mchapagai.library.widget.EndlessScrollListener;
import com.mchapagai.movies.R;
import com.mchapagai.movies.activity.MovieDetailsActivity;
import com.mchapagai.movies.adapter.MoviesGridAdapter;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.Sort;
import com.mchapagai.movies.model.binding.MovieResponse;
import com.mchapagai.movies.view_model.MovieViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import javax.inject.Inject;
import org.reactivestreams.Publisher;
import retrofit2.HttpException;
import retrofit2.Response;

public class DiscoverMoviesFragment extends BaseFragment {

    private static final String TAG = DiscoverMoviesFragment.class.getSimpleName();

    private static final int COLUMN_COUNT = 2;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Sort sort = Sort.MOST_POPULAR;

    private MoviesGridAdapter moviesGridAdapter;

    private PublishProcessor<Integer> pagination = PublishProcessor.create();

    private boolean isLoading = false;

    private boolean isMenuSortChanged = true;

    private int pageNumber = 1;

    @BindView(R.id.common_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.common_page_loader)
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
        View view = inflater.inflate(R.layout.common_fragment_container, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), COLUMN_COUNT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(final int currentPage, final int totalItemCount, final View view) {
                if (!isLoading) {
                    pagination.onNext(pageNumber++);
                }
            }
        });

        return view;
    }

    private Flowable<MovieResponse> movieResponseItems(int page, String sort) {
        return movieViewModel.discoverMovies(page, sort);
    }

    private void loadMovies() {

        pageLoader.setVisibility(View.VISIBLE);
        Disposable disposable = pagination.doFinally(() -> pageLoader.setVisibility(View.GONE))
                .concatMap(new Function<Integer, Publisher<MovieResponse>>() {
                    /**
                     * Apply some calculation to the input value and return some other value.`
                     *
                     * @param page the input value
                     * @return the output value
                     */
                    @Override
                    public Publisher<MovieResponse> apply(Integer page) {
                        return movieResponseItems(page, sort.toString());
                    }
                }).doOnSubscribe(s -> isLoading = true)
                .doOnNext(new Consumer<MovieResponse>() {
                    /**
                     * Consume the given value.
                     *
                     * @param movieResponse the value
                     */
                    @Override
                    public void accept(MovieResponse movieResponse) {
                        if (isMenuSortChanged) {
                            moviesGridAdapter = new MoviesGridAdapter(movieResponse.getMovies());
                            recyclerView.setAdapter(moviesGridAdapter);
                        } else {
                            moviesGridAdapter.notifyDataChange(movieResponse.getMovies());
                        }

                        moviesGridAdapter.setOnItemClickListener((movies, position) -> {
                            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                            setSharedElementEnterTransition(
                                    new TransitionSet().addTransition(new ChangeBounds())
                                            .addTransition(new ChangeImageTransform())
                                            .addTransition(new ChangeTransform()));
                            setEnterTransition(new Fade());
                            setExitTransition(new Fade());
                            setSharedElementReturnTransition(
                                    new TransitionSet().addTransition(new ChangeBounds())
                                            .addTransition(new ChangeImageTransform())
                                            .addTransition(new ChangeTransform()));
                            intent.putExtra(Constants.MOVIE_DETAILS, movies);
                            startActivity(intent);
                        });

                        isMenuSortChanged = false;
                        isLoading = false;
                        pageLoader.setVisibility(View.GONE);
                    }
                })
                .doOnError(throwable -> {
                    if (throwable instanceof HttpException) {
                        Response<?> response = ((HttpException) throwable).response();
                        Log.d(TAG, response.message());
                    }

                })
                .subscribe();

        compositeDisposable.add(disposable);
        pagination.onNext(pageNumber);
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
        isMenuSortChanged = true;
        pageNumber = 1;
        loadMovies();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menu_sort_popularity:
                onSortChanged(Sort.MOST_POPULAR);
                break;
            case R.id.menu_sort_vote_count:
                onSortChanged(Sort.MOST_RATED);
                break;
            case R.id.menu_sort_vote_average:
                onSortChanged(Sort.TOP_RATED);
                break;
        }
        item.setChecked(!item.isChecked());
        return super.onOptionsItemSelected(item);
    }
}
