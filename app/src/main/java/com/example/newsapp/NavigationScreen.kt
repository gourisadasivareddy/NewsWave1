package com.example.newswave

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.View.LoginScreen
import com.example.newsapp.View.NewsData
import com.example.newsapp.View.SignUp
import com.example.newsapp.View.SplashScreen
import com.example.newsapp.ViewModel.NewsViewModel

@Composable
fun NavigationScreen(viewModel: NewsViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreenDest.route
    ) {
        composable(route = DestinationScreen.SplashScreenDest.route) {
            SplashScreen(navController = navController)
        }

        composable(route = DestinationScreen.LoginScreenDest.route) {
            LoginScreen( navController = navController)
        }
        composable(route = DestinationScreen.SignUpScreenDest.route) {
            SignUp( navController = navController)
        }
        composable(route = DestinationScreen.MainScreenDest.route) {
            NewsData(viewModel = viewModel, navController = navController)
        }


    }
}