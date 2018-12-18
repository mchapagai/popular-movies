package com.mchapagai.movies.api.impl;
import com.mchapagai.movies.api.LoginAPI;
import com.mchapagai.movies.api.MovieAPI;
import com.mchapagai.movies.service.LoginService;
import com.mchapagai.movies.service.MovieService;
import com.mchapagai.movies.service.ServiceModule;

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
