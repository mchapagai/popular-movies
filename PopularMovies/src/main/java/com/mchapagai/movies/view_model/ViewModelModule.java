package com.mchapagai.movies.view_model;

import com.mchapagai.core.api.MovieAPI;
import com.mchapagai.movies.api.LoginAPI;
import com.mchapagai.movies.api.SearchAPI;
import com.mchapagai.movies.api.ShowsAPI;
import com.mchapagai.movies.api.impl.APIModule;
import com.mchapagai.movies.view_model.impl.LoginViewModelImpl;
import com.mchapagai.movies.view_model.impl.MovieViewModelImpl;
import com.mchapagai.movies.view_model.impl.SearchViewModelImpl;
import com.mchapagai.movies.view_model.impl.ShowsViewModelImpl;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = APIModule.class
)
public class ViewModelModule {

    @Provides
    MovieViewModel providesMovieViewModel(Provider<MovieAPI> api) {
        return new MovieViewModelImpl(api);
    }

    @Provides
    SearchViewModel providesSearchViewModel(SearchAPI movieAPI) {
        return new SearchViewModelImpl(movieAPI);
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