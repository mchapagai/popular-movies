package com.mchapagai.movies.service;

import com.mchapagai.core.common.RetrofitClient;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    MovieService provideMovieService() {
        return RetrofitClient.INSTANCE.createService(MovieService.class);
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
