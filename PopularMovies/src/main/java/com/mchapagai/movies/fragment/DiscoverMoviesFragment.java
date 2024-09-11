package com.mchapagai.movies.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeTransform;
import androidx.transition.Fade;
import androidx.transition.TransitionSet;

import com.mchapagai.core.response.movies.MovieListResponse;
import com.mchapagai.core.response.movies.MovieResponse;
import com.mchapagai.movies.R;
import com.mchapagai.movies.activity.MovieDetailsActivity;
import com.mchapagai.movies.adapter.MoviesGridAdapter;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.Sort;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.mchapagai.movies.view_model.SearchViewModel;
import com.mchapagai.movies.views.PageLoader;
import com.mchapagai.movies.widget.EndlessScrollListener;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;
import retrofit2.HttpException;
import retrofit2.Response;

public class DiscoverMoviesFragment extends BaseFragment {

    private static final String TAG = DiscoverMoviesFragment.class.getSimpleName();
    private static final int COLUMN_COUNT = 2;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Sort sort = Sort.MOST_POPULAR;
    private MoviesGridAdapter moviesGridAdapter;
    private final PublishProcessor<Integer> pagination = PublishProcessor.create();
    private SearchView searchView;
    private boolean isLoading = false;
    private boolean isMenuSortChanged = true;
    private int pageNumber = 1;
    RecyclerView recyclerView;
    PageLoader pageLoader;

    @Inject
    SearchViewModel searchViewModel;

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

        recyclerView = view.findViewById(R.id.common_recycler_view);
        pageLoader = view.findViewById(R.id.common_page_loader);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), COLUMN_COUNT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(final int currentPage, final int totalItemCount,
                    final View view) {
                if (!isLoading) {
                    pagination.onNext(pageNumber++);
                }
            }
        });

        return view;
    }

    private Flowable<MovieListResponse> movieResponseItems(int page, String sort) {
        return movieViewModel.discoverMovies(page, sort);
    }

    private void loadMovies() {

        pageLoader.setVisibility(View.VISIBLE);
        Disposable disposable = pagination.doFinally(() -> pageLoader.setVisibility(View.GONE))
                .concatMap(new Function<Integer, Publisher<MovieListResponse>>() {
                    /**
                     * Apply some calculation to the input value and return some other value.`
                     *
                     * @param page the input value
                     * @return the output value
                     */
                    @Override
                    public Publisher<MovieListResponse> apply(Integer page) {
                        return movieResponseItems(page, sort.toString());
                    }
                }).doOnSubscribe(s -> isLoading = true)
                .doOnNext(new Consumer<MovieListResponse>() {
                    /**
                     * Consume the given value.
                     *
                     * @param movieResponse the value
                     */
                    @Override
                    public void accept(MovieListResponse movieResponse) {
                        if (isMenuSortChanged) {
                            moviesGridAdapter = new MoviesGridAdapter(movieResponse.getMovies());
                            recyclerView.setAdapter(moviesGridAdapter);
                        } else {
                            moviesGridAdapter.notifyDataChange(movieResponse.getMovies());
                        }

                        moviesGridAdapter.setOnItemClickListener((movieId, position) -> {
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
                            intent.putExtra(Constants.MOVIE_DETAILS, movieId);
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

    private void searchSubscription() {
        instantiateSearchQuery(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(s -> !s.isEmpty())
                .switchMap(
                        (Function<String, ObservableSource<List<MovieResponse>>>) s -> searchResponse(s))
                .subscribe(
                        new DisposableObserver<List<MovieResponse>>() {
                            @Override
                            public void onNext(List<MovieResponse> moviesList) {
                                moviesGridAdapter = new MoviesGridAdapter(moviesList);
                                recyclerView.setAdapter(moviesGridAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );
        pagination.onNext(pageNumber);
    }


    private Observable<List<MovieResponse>> searchResponse(String query) {
        return searchViewModel.searchMovies(query)
                .flatMapIterable(movieResponse -> movieResponse.getMovies()).distinct(
                        movies -> movies.getTitle()).toList().toObservable();
    }

    private Observable<String> instantiateSearchQuery(SearchView view) {
        PublishSubject<String> publishSubject = PublishSubject.create();
        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(@NonNull String query) {
                if (!TextUtils.isEmpty(query)) {
                    publishSubject.onComplete();
                    publishSubject.onNext(query);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
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

        SearchManager manager = (SearchManager) getActivity().getSystemService(
                Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchSubscription();
                moviesGridAdapter.getFilter().filter(newText);
                return true;
            }
        });

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
            requireActivity().finish();
            return true;
        }

        if (item.getItemId() == R.id.menu_sort_popularity) {
            onSortChanged(Sort.MOST_POPULAR);
        } else if (item.getItemId() == R.id.menu_sort_vote_count) {
            onSortChanged(Sort.MOST_RATED);
        } else if (item.getItemId() == R.id.menu_sort_vote_average) {
            onSortChanged(Sort.TOP_RATED);
        }
        item.setChecked(!item.isChecked());

        return super.onOptionsItemSelected(item);
    }
}
