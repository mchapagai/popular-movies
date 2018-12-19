package com.mchapagai.movies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.mchapagai.movies.R;
import com.mchapagai.movies.common.BaseActivity;

public class LaunchActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity_container);
        // Fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, LandingActivity.class));

            finish();
        }, SPLASH_TIME_OUT);
    }
}
