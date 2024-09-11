package com.mchapagai.movies.api.impl;

import com.mchapagai.core.api.MovieAPI;
import com.mchapagai.core.api.MoviesAPIImpl;
import com.mchapagai.core.service.MovieService;
import com.mchapagai.movies.api.LoginAPI;
import com.mchapagai.movies.api.SearchAPI;
import com.mchapagai.movies.api.ShowsAPI;
import com.mchapagai.movies.service.LoginService;
import com.mchapagai.movies.service.MovieSearchService;
import com.mchapagai.movies.service.ServiceModule;
import com.mchapagai.movies.service.ShowsService;
import com.mchapagai.movies.view_model.MovieViewModel;
import com.mchapagai.movies.view_model.impl.MovieViewModelImpl;

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
    ShowsAPI providesTvAPI(Provider<ShowsService> service) {
        return new ShowsAPIImpl(service);
    }
}
