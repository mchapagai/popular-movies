package com.mchapagai.klutter.injection;

import com.mchapagai.klutter.activity.CreditDetailsActivity;
import com.mchapagai.klutter.activity.DiscoverMoviesActivity;
import com.mchapagai.klutter.activity.DiscoverOnTheAirActivity;
import com.mchapagai.klutter.activity.LandingActivity;
import com.mchapagai.klutter.activity.LoginActivity;
import com.mchapagai.klutter.activity.MovieDetailsActivity;
import com.mchapagai.klutter.activity.ProfileActivity;
import com.mchapagai.klutter.activity.ShowDetailsActivity;
import com.mchapagai.klutter.activity.SplashActivity;
import com.mchapagai.klutter.activity.WelcomeActivity;
import com.mchapagai.klutter.common.BaseActivity;
import com.mchapagai.klutter.common.BaseFragment;
import com.mchapagai.klutter.features.about.activity.AboutActivity;
import com.mchapagai.klutter.fragment.DiscoverMoviesFragment;
import com.mchapagai.klutter.fragment.DiscoverOnTheAirFragment;
import com.mchapagai.klutter.fragment.InfoFragment;
import com.mchapagai.klutter.fragment.ReviewsFragment;
import com.mchapagai.klutter.fragment.TrailerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ViewsModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract BaseActivity baseActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract BaseFragment baseFragment();

    @ActivityScope
    @ContributesAndroidInjector
    abstract DiscoverMoviesFragment discoverMoviesFragment();

    @ActivityScope
    @ContributesAndroidInjector
    abstract LandingActivity landingActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract DiscoverMoviesActivity discoverMoviesActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract MovieDetailsActivity movieDetailsActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract WelcomeActivity welcomeActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SplashActivity launchActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract AboutActivity aboutActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract CreditDetailsActivity creditDetailsActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract DiscoverOnTheAirActivity discoverOnTheAirActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract DiscoverOnTheAirFragment discoverOnTheAirFragment();

    @ActivityScope
    @ContributesAndroidInjector
    abstract ShowDetailsActivity showDetailsActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract InfoFragment overviewFragment();

    @ActivityScope
    @ContributesAndroidInjector
    abstract ReviewsFragment reviewsFragment();

    @ActivityScope
    @ContributesAndroidInjector
    abstract TrailerFragment trailerFragment();
}