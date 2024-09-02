package com.mchapagai.movies.common;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dagger.android.AndroidInjection;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
    }

    @Override
    public void setContentView(int resId) {
        super.setContentView(resId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.baseApplication.mustDie(this);
    }
}
