package com.mchapagai.movies.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mchapagai.core.response.shows.ShowResponse;
import com.mchapagai.movies.fragment.InfoFragment;
import com.mchapagai.movies.fragment.ReviewsFragment;
import com.mchapagai.movies.fragment.TrailerFragment;

public class ShowDetailsAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 3;
    private String tablayoutTitles[] = new String[]{"Overview", "Videos", "Reviews"};
    private ShowResponse onTheAir;

    public ShowDetailsAdapter(final FragmentManager fm, ShowResponse onTheAir) {
        super(fm);
        this.onTheAir = onTheAir;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return switch (position) {
            case 0 -> InfoFragment.newInstance(onTheAir);
            case 1 -> TrailerFragment.newInstance(onTheAir);
            case 2 -> ReviewsFragment.newInstance(onTheAir);
            default -> null;
        };
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tablayoutTitles[position];
    }

}
