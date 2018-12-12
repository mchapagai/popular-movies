package com.example.mchapagai.view_model;

import com.example.mchapagai.service.LoginService;
import com.example.mchapagai.service.MovieService;
import com.example.mchapagai.service.ServiceFactory;
import com.example.mchapagai.view_model.impl.MovieViewModelImpl;
import com.example.mchapagai.view_model.impl.LoginViewModelImpl;

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

    @Provides
    @Singleton
    LoginService providesLoginViewModelService() {
        return ServiceFactory.createService(LoginService.class);
    }

    @Provides
    @Singleton
    LoginViewModel providesViewModel(Provider<LoginService> loginService) {
        return new LoginViewModelImpl(loginService);
    }

}