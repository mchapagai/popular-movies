package com.mchapagai.movies

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mchapagai.compose.navigation.Navigation
import com.mchapagai.compose.theme.MoviesTheme


@Composable
fun MovieApplication(
) {
    MoviesTheme {
        Surface {
            Navigation()
        }
    }
}
