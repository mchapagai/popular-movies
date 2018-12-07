package com.example.mchapagai.view_model;

import com.example.mchapagai.service.ServiceModule;

import dagger.Module;

@Module(
        includes = {ServiceModule.class}
)
public class ViewModelModule {
}