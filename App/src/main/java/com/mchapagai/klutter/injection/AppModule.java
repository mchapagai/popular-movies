package com.mchapagai.klutter.injection;

import android.content.Context;

import com.mchapagai.klutter.common.BaseApplication;
import com.mchapagai.klutter.utils.PreferencesHelper;
import com.mchapagai.klutter.view_model.ViewModelModule;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(
        includes = ViewModelModule.class
)
class AppModule {

    @Provides
    @Singleton
    Context context() {
        return BaseApplication.getApplicationContextInstance();
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(Context context) {
        return new PreferencesHelper(context);
    }

}