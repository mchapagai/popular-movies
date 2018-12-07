package com.example.mchapagai.common;

import android.app.Application;
import android.content.Context;

import com.example.mchapagai.injection.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import dagger.android.AndroidInjector;

public class BaseApplication extends Application {

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
