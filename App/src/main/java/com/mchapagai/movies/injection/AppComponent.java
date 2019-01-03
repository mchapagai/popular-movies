package com.mchapagai.movies.injection;


import com.mchapagai.movies.common.BaseApplication;

import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AndroidSupportInjectionModule.class,
                AppModule.class,
                ViewsModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

}