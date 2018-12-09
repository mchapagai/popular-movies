package com.example.mchapagai.injection;

import android.content.Context;

import com.example.mchapagai.common.BaseApplication;
import com.example.mchapagai.view_model.ViewModelModule;

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