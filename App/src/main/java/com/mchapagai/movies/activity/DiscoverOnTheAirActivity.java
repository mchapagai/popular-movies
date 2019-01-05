package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mchapagai.library.utils.MaterialDialogUtils;
import com.mchapagai.library.views.PageLoader;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.tv.TvGridAdapter;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.model.tv.OnTheAir;
import com.mchapagai.movies.model.tv.binding.OnTheAirResponse;
import com.mchapagai.movies.view_model.TvViewModel;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

public class DiscoverOnTheAirActivity extends BaseActivity {

    private static final String TAG = LandingActivity.class.getSimpleName();

    private static final int COLUMN_COUNT = 2;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.shows_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.showa_page_loader)
    PageLoader pageLoader;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    TvViewModel tvViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_shows_activity_container);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, COLUMN_COUNT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void showsResponseItems(OnTheAirResponse response) {
        final List<OnTheAir> ontheAirItems = response.getOnTheAir();
        recyclerView.setAdapter(new TvGridAdapter(ontheAirItems));
    }

    private void loadMovies() {
        pageLoader.setVisibility(View.VISIBLE);
        compositeDisposable.add(tvViewModel.discoverOnTheAirShows()
                .doFinally(() -> pageLoader.setVisibility(View.GONE))
                .subscribe(
                        response -> {
                            showsResponseItems(response);
                            Log.d(TAG, response.toString());
                        }, throwable -> MaterialDialogUtils.showDialog(DiscoverOnTheAirActivity.this,
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
