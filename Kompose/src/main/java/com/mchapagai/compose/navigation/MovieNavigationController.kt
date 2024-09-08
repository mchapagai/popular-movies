package com.mchapagai.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mchapagai.compose.movies.MovieDetailsScreen
import com.mchapagai.compose.navigation.Routes.MOVIE_ID_ARG
import com.mchapagai.compose.screen.HomeScreen

// Explanation:
//Route Constants: The Routes object defines constants for each screen's route, making them reusable and preventing typos.
//MOVIE_ID_ARG: A constant for the movie ID argument key.
//Navigation Composable: Sets up the NavHost with the starting route as Routes.MOVIE_SCREEN.
//MovieScreen Route: The composable for Routes.MOVIE_SCREEN takes an onMovieClick lambda to handle navigation to the details screen.
//MovieDetailsScreen Route:
//Uses a dynamic route with the movieId argument.
//Defines the movieId argument as an integer using NavType.IntType.
//Retrieves the movieId from the backStackEntry and passes it to MovieDetailsScreen.
//Other Routes: The remaining screens have simple composable routes. How to Use:
//Include this code in your Jetpack Compose project.
//Call the Navigation composable at the top level of your UI hierarchy.
//In MovieScreen, call onMovieClick with the appropriate movie ID when a movie item is clicked. This setup provides a reusable and type-safe way to navigate between your screens using Compose Navigation.
@Composable
fun MovieNavigationController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MOVIE_SCREEN) {
        composable(Routes.MOVIE_SCREEN) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate("${Routes.MOVIE_DETAILS_SCREEN}/$movieId")
                }
            )
        }
        composable(
            route = "${Routes.MOVIE_DETAILS_SCREEN}/{$MOVIE_ID_ARG}",
            arguments = listOf(navArgument(MOVIE_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(MOVIE_ID_ARG) ?: 0
            MovieDetailsScreen(movieId, onPressBack = {
                navController.popBackStack()
            })
        }
        composable(Routes.SHOWS_SCREEN) { ShowsScreen() }
        composable(Routes.PEOPLE_SCREEN) { PeopleScreen() }
        composable(Routes.ABOUT_SCREEN) { AboutScreen() }
    }
}

// Other screens (ShowsScreen, PeopleScreen, AboutScreen) remain the same

@Composable
fun ShowsScreen() {
}

@Composable
fun PeopleScreen() {
}

@Composable
fun AboutScreen() {
}