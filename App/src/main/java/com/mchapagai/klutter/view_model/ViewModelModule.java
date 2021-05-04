package com.mchapagai.klutter.view_model;

import com.mchapagai.klutter.api.LoginAPI;
import com.mchapagai.klutter.api.MovieAPI;
import com.mchapagai.klutter.api.ShowsAPI;
import com.mchapagai.klutter.api.impl.APIModule;
import com.mchapagai.klutter.view_model.impl.LoginViewModelImpl;
import com.mchapagai.klutter.view_model.impl.MovieViewModelImpl;
import com.mchapagai.klutter.view_model.impl.ShowsViewModelImpl;

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