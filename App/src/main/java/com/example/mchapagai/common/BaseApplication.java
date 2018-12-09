package com.example.mchapagai.common;

import android.content.Context;

import com.example.mchapagai.injection.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    private static Context context;
    private static BaseApplication baseApplication;

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        context = this.getApplicationContext();
    }

    public static Context getApplicationContextInstance() {
        return context;
    }

    public AndroidInjector<BaseApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    public static BaseApplication getBaseApplication() {
        if (baseApplication == null) {
            baseApplication = new BaseApplication();
        }
        return baseApplication;
    }

}
