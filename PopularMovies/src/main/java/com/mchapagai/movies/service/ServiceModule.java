package com.mchapagai.movies.service;

import com.mchapagai.core.common.RetrofitClient;
import com.mchapagai.core.service.MovieService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    MovieService providesMovieService() {
        return RetrofitClient.INSTANCE.createService(MovieService.class);
    }

    @Provides
    MovieSearchService provideMovieSearchService() {
        return RetrofitClient.INSTANCE.createService(MovieSearchService.class);
    }

    @Provides
    LoginService provideLoginService() {
        return RetrofitClient.INSTANCE.createService(LoginService.class);
    }

    @Provides
    ShowsService provideTvService() {
        return RetrofitClient.INSTANCE.createService(ShowsService.class);
    }

}
