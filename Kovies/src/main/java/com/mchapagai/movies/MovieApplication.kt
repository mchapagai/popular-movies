package com.mchapagai.movies

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mchapagai.compose.navigation.MovieNavigationController
import com.mchapagai.compose.theme.MoviesTheme


@Composable
fun MovieApplication() {
    MoviesTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MovieNavigationController()
        }
    }
}
