package com.mchapagai.movies.api.impl;

import com.mchapagai.core.api.MovieAPI;
import com.mchapagai.core.api.MoviesAPIImpl;
import com.mchapagai.core.api.PeopleAPI;
import com.mchapagai.core.api.PeopleAPIImpl;
import com.mchapagai.core.api.ShowAPI;
import com.mchapagai.core.api.ShowAPIImpl;
import com.mchapagai.core.service.MovieService;
import com.mchapagai.core.service.PeopleService;
import com.mchapagai.core.service.ShowService;
import com.mchapagai.movies.api.LoginAPI;
import com.mchapagai.movies.api.SearchAPI;
import com.mchapagai.movies.service.LoginService;
import com.mchapagai.movies.service.MovieSearchService;
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
    MovieAPI providesMovieAPI(MovieService service) {
        return new MoviesAPIImpl(service);
    }

    @Provides
    @Singleton
    PeopleAPI providesPeopleAPI(PeopleService service) {
        return new PeopleAPIImpl(service);
    }

    @Provides
    @Singleton
    SearchAPI providesSearchAPI(Provider<MovieSearchService> service) {
        return new SearchAPIImpl(service);
    }

    @Provides
    @Singleton
    LoginAPI providesLoginAPI(Provider<LoginService> service) {
        return new LoginAPIImpl(service);
    }

    @Singleton
    @Provides
    ShowAPI providesShowAPI(ShowService service) {
        return new ShowAPIImpl(service);
    }
}
