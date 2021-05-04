package com.mchapagai.klutter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.mchapagai.klutter.R;
import com.mchapagai.klutter.common.BaseActivity;
import com.mchapagai.klutter.common.Constants;
import com.mchapagai.klutter.utils.PreferencesHelper;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_container);

        // Fullscreen
        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(() -> {
            if (preferencesHelper.isAppInstalledForTheFirstTime()) {
                startActivity(new Intent(this, WelcomeActivity.class));
            } else {
                startActivity(new Intent(this, LandingActivity.class));
            }
            finish();
        }, Constants.SPLASH_TIME_OUT);
    }
}
