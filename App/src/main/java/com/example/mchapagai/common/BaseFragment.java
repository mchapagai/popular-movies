package com.example.mchapagai.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {

    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
