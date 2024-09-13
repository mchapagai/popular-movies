package com.mchapagai.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mchapagai.compose.about.AboutScreen
import com.mchapagai.compose.movies.MovieDetailsScreen
import com.mchapagai.compose.movies.SearchScreen
import com.mchapagai.compose.navigation.Routes.CREDIT_ID_ARGUMENT
import com.mchapagai.compose.navigation.Routes.MOVIE_ID_ARGUMENT
import com.mchapagai.compose.people.PersonDetailsScreen
import com.mchapagai.compose.screen.HomeScreen


@Composable
fun MovieNavigationController(
    navController: NavHostController = rememberNavController()
) {
    MovieNavGraph(navController = navController)
}

@Composable
fun MovieNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.MOVIE_SCREEN) {
        movieNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.movieNavGraph(navController: NavHostController) {
    composable(Routes.MOVIE_SCREEN) {
        HomeScreen(
            onMovieClick = { movieId ->
                navController.navigate("${Routes.MOVIE_DETAILS_SCREEN}/$movieId")
            },
            onClickSearch = {
                navController.navigate(Routes.SEARCH_SCREEN)
            },
            onClickInfo = {
                navController.navigate(Routes.ABOUT_SCREEN)
            }
        )
    }
    composable(
        route = "${Routes.MOVIE_DETAILS_SCREEN}/{$MOVIE_ID_ARGUMENT}",
        arguments = listOf(navArgument(MOVIE_ID_ARGUMENT) { type = NavType.IntType })
    ) { backStackEntry ->
        val movieId = backStackEntry.arguments?.getInt(MOVIE_ID_ARGUMENT) ?: 0
        MovieDetailsScreen(
            movieId,
            onPressBack = {
                navController.popBackStack()
            },
            onClickCreditItem = { creditId ->
                navController.navigate("${Routes.PEOPLE_SCREEN}/$creditId")
            }
        )
    }
    composable(
        route = "${Routes.PEOPLE_SCREEN}/{$CREDIT_ID_ARGUMENT}",
        arguments = listOf(navArgument(CREDIT_ID_ARGUMENT) { type = NavType.IntType })
    ) { backStackEntry ->
        val creditId = backStackEntry.arguments?.getInt(CREDIT_ID_ARGUMENT) ?: -1
        PersonDetailsScreen(
            creditId,
            onClickToolbar = {
                navController.popBackStack()
            }
        )
    }
    composable(Routes.SEARCH_SCREEN) {
        SearchScreen(
            onPressBack = {
                navController.popBackStack()
            },
            onClickSearchItem = { movieId ->
                navController.navigate("${Routes.MOVIE_DETAILS_SCREEN}/$movieId")
            }
        )
    }
    composable(Routes.ABOUT_SCREEN) {
        AboutScreen(
            onBackPress = {
                navController.popBackStack()
            }
        )
    }
}