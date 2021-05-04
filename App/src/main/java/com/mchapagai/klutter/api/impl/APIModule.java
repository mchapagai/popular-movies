package com.mchapagai.klutter.api.impl;

import com.mchapagai.klutter.api.LoginAPI;
import com.mchapagai.klutter.api.MovieAPI;
import com.mchapagai.klutter.api.ShowsAPI;
import com.mchapagai.klutter.service.LoginService;
import com.mchapagai.klutter.service.MovieService;
import com.mchapagai.klutter.service.ServiceModule;
import com.mchapagai.klutter.service.ShowsService;

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

    @Singleton
    @Provides
    ShowsAPI providesTvAPI(Provider<ShowsService> service) {
        return new ShowsAPIImpl(service);
    }
}
