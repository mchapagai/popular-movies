package com.mchapagai.klutter.injection;

import com.mchapagai.klutter.common.BaseApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
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