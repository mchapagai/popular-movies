package com.mchapagai.library.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 5;
    private GridLayoutManager layoutManager;
    private boolean loading = false;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private int startingPageIndex = 0;

    public EndlessScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + VISIBLE_THRESHOLD) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }

    }

    public abstract void onLoadMore(int currentPage, int totalItemCount, View view);
}
