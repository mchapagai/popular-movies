package com.mchapagai.movies.injection;

import android.content.Context;

import com.mchapagai.movies.common.BaseApplication;
import com.mchapagai.movies.view_model.ViewModelModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = ViewModelModule.class
)
class AppModule {

    @Singleton
    @Provides
    BaseApplication provideBaseApplication() {
        return BaseApplication.getBaseApplication();
    }

    @Provides
    @Singleton
    Context context() {
        return BaseApplication.getApplicationContextInstance();
    }

}