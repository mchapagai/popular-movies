package com.example.mchapagai.api.impl;

import com.example.mchapagai.api.LoginAPI;
import com.example.mchapagai.api.MovieAPI;
import com.example.mchapagai.service.LoginService;
import com.example.mchapagai.service.MovieService;
import com.example.mchapagai.service.ServiceModule;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = ServiceModule.class
)
public class APIModule {

    @Provides
    @Singleton
    MovieAPI providesMovieAPI(Provider<MovieService> service) {
        return new MovieAPIImpl(service);
    }

    @Provides
    @Singleton
    LoginAPI providesLoginAPI(Provider<LoginService> service) {
        return new LoginAPIImpl(service);
    }
}
