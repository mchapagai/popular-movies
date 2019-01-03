package com.mchapagai.movies.injection;

import android.content.Context;
import com.mchapagai.movies.common.BaseApplication;
import com.mchapagai.movies.view_model.ViewModelModule;
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

}