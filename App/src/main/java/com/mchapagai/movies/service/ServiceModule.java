package com.mchapagai.movies.service;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    MovieService provideMovieService() {
        return ServiceFactory.createService(MovieService.class);
    }

    @Provides
    LoginService provideLoginService() {
        return ServiceFactory.createService(LoginService.class);
    }

}
