package com.mchapagai.movies.common;

import android.content.Context;
import androidx.multidex.MultiDex;
import com.mchapagai.movies.injection.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    private static Context context;
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
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

    /**
     * Set the base context for this ContextWrapper.  All calls will then be
     * delegated to the base context.  Throws
     * IllegalStateException if a base context has already been set.
     *
     * @param base The new base context for this wrapper.
     */
    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
