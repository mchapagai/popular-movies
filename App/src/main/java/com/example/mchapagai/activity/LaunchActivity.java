package com.example.mchapagai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.mchapagai.R;
import com.example.mchapagai.common.BaseActivity;
import com.example.mchapagai.utils.SharedPreferencesUtils;

public class LaunchActivity extends BaseActivity {

    SharedPreferencesUtils spUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity_container);
        spUtils = new SharedPreferencesUtils(this);
        // Fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            // Launch LoginActivity if not logged in or else navigate user to LandingActivity
            if (spUtils.isSignedIn()) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(LaunchActivity.this, LandingActivity.class);

            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(LaunchActivity.this, LoginActivity.class);

            }
            startActivity(intent);

            finish();
        }, SPLASH_TIME_OUT);
    }
}
