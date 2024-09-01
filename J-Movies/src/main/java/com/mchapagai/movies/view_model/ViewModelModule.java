package com.mchapagai.movies.view_model;

import com.mchapagai.movies.api.LoginAPI;
import com.mchapagai.movies.api.MovieAPI;
import com.mchapagai.movies.api.ShowsAPI;
import com.mchapagai.movies.api.impl.APIModule;
import com.mchapagai.movies.view_model.impl.LoginViewModelImpl;
import com.mchapagai.movies.view_model.impl.MovieViewModelImpl;
import com.mchapagai.movies.view_model.impl.ShowsViewModelImpl;

import dagger.Module;
import dagger.Provides;

@Module(
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

    @Provides
    ShowsViewModel providesTvViewModel(ShowsAPI showsAPI) {
        return new ShowsViewModelImpl(showsAPI);
    }

}