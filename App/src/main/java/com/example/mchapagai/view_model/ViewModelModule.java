package com.example.mchapagai.view_model;

import com.example.mchapagai.api.LoginAPI;
import com.example.mchapagai.api.MovieAPI;
import com.example.mchapagai.api.impl.APIModule;
import com.example.mchapagai.view_model.impl.LoginViewModelImpl;
import com.example.mchapagai.view_model.impl.MovieViewModelImpl;

import dagger.Module;
import dagger.Provides;

@Module (
        includes = APIModule.class
)
public class ViewModelModule {

    @Provides
    MovieViewModel providesMovieViewModel(MovieAPI movieAPI) {
        return new MovieViewModelImpl(movieAPI);
    }

    @Provides
    LoginViewModel providesLoginViewModel(LoginAPI loginAPI) {
        return new LoginViewModelImpl(loginAPI);
    }


}