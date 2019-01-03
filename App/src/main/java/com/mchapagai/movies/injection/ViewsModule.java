package com.mchapagai.movies.injection;

import com.mchapagai.movies.activity.AboutActivity;
import com.mchapagai.movies.activity.CreditDetailsActivity;
import com.mchapagai.movies.activity.DiscoverMoviesActivity;
import com.mchapagai.movies.activity.LandingActivity;
import com.mchapagai.movies.activity.LoginActivity;
import com.mchapagai.movies.activity.MovieDetailsActivity;
import com.mchapagai.movies.activity.SplashActivity;
import com.mchapagai.movies.activity.WelcomeActivity;
import com.mchapagai.movies.common.BaseActivity;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.fragment.DiscoverMoviesFragment;
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
}