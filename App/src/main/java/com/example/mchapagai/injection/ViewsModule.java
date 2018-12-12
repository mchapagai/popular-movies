package com.example.mchapagai.injection;

import com.example.mchapagai.activity.AboutActivity;
import com.example.mchapagai.activity.LandingActivity;
import com.example.mchapagai.activity.LaunchActivity;
import com.example.mchapagai.activity.LoginActivity;
import com.example.mchapagai.activity.MovieDetailsActivity;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.BaseFragment;
import com.example.mchapagai.fragment.LandingFragment;

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
    abstract LandingActivity landingActivity();
    @ActivityScope
    @ContributesAndroidInjector
    abstract LandingFragment landingFragment();

    @ActivityScope
    @ContributesAndroidInjector
    abstract MovieDetailsActivity movieDetailsActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract LaunchActivity launchActivity();
    @ActivityScope
    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();
    @ActivityScope
    @ContributesAndroidInjector
    abstract AboutActivity aboutActivity();
}