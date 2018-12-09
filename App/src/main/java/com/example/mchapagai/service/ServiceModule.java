package com.example.mchapagai.service;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    MovieService provideMovieService() {
        return ServiceFactory.createService(MovieService.class);
    }

}
