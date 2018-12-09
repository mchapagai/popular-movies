package com.example.mchapagai.view_model;

import com.example.mchapagai.service.MovieService;
import com.example.mchapagai.service.ServiceFactory;
import com.example.mchapagai.view_model.impl.MovieViewModelImpl;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    @Singleton
    MovieService providesMovieService() {
        return ServiceFactory.createService(MovieService.class);
    }

    @Provides
    @Singleton
    MovieViewModel providesMovieViewModel(Provider<MovieService> movieService) {
        return new MovieViewModelImpl(movieService);
    }

}