package com.example.mchapagai.injection;

import com.example.mchapagai.activity.AboutActivity;
import com.example.mchapagai.activity.MainActivity;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.common.BaseFragment;

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
    abstract MainActivity mainActivity();
    @ActivityScope
    @ContributesAndroidInjector
    abstract AboutActivity aboutActivity();
}