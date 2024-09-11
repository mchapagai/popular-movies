package com.mchapagai.movies.service;

import com.mchapagai.core.common.RetrofitClient;
import com.mchapagai.core.service.MovieService;
import com.mchapagai.core.service.PeopleService;
import com.mchapagai.core.service.ShowService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    MovieService providesMovieService() {
        return RetrofitClient.INSTANCE.createService(MovieService.class);
    }

    @Provides
    PeopleService providesPeopleService() {
        return RetrofitClient.INSTANCE.createService(PeopleService.class);
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
    ShowService provideShowService() {
        return RetrofitClient.INSTANCE.createService(ShowService.class);
    }

}
