package com.example.mchapagai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.library.utils.MaterialDialogUtils;
import com.example.library.views.PageLoader;
import com.example.mchapagai.R;
import com.example.mchapagai.adapter.MoviesGridAdapter;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.Constants;
import com.example.mchapagai.model.Movies;
import com.example.mchapagai.model.Sort;
import com.example.mchapagai.model.binding.MovieResponse;
import com.example.mchapagai.view_model.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LandingActivity extends BaseActivity implements MoviesGridAdapter.OnItemClickListener {

    private static final String TAG = LandingActivity.class.getSimpleName();

    private static final int COLUMN_COUNT = 2;
    private List<Movies> movieItems = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recyclerView;
    private PageLoader pageLoader;
    private Sort sort = Sort.MOST_POPULAR;

    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.movies_recycler_view);
        pageLoader = findViewById(R.id.movies_page_loader);
        GridLayoutManager layoutManager = new GridLayoutManager(this, COLUMN_COUNT);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.landing_fragment_container, new LandingFragment()).commit();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.landing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        return super.onOptionsItemSelected(item);
    }

    private void onSortChanged(Sort sort) {
        this.sort = sort;
    }

    private void movieResponseItems(MovieResponse response) {
        movieItems = response.getMovies();
        recyclerView.setAdapter(new MoviesGridAdapter(movieItems, this));
    }

    private void loadMovies() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(movieViewModel.discoverMovies(sort.toString())
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(
                        response -> {
                            movieResponseItems(response);
                            Log.d(TAG, response.toString());
                        }, throwable -> MaterialDialogUtils.showDialog(LandingActivity.this,
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

    @Override
    public void onClickItem(Movies movies, int position) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        startActivity(intent.putExtra(Constants.MOVIE_DETAILS, movies));
    }
}
