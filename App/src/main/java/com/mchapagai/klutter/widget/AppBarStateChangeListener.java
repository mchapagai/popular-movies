package com.mchapagai.klutter.widget;

import com.google.android.material.appbar.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public void onOffsetChanged(final AppBarLayout appBarLayout, final int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onExpanded(appBarLayout);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onCollapsed(appBarLayout);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onIdle(appBarLayout);
            }
            mCurrentState = State.IDLE;
        }
    }

    protected abstract void onExpanded(AppBarLayout appBarLayout);

    protected abstract void onCollapsed(AppBarLayout appBarLayout);

    protected abstract void onIdle(AppBarLayout appBarLayout);
}
