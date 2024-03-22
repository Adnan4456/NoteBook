package com.example.notebook.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.notebook.feature_forget_password.presentation.ui.ForgetScreen
import com.example.notebook.feature_login.presentation.ui.LoginScreen
import com.example.notebook.feature_note.presentation.util.AuthScreen
import com.example.notebook.feature_signup.presentation.ui.SignUpScreen
import com.example.notebook.feature_splash_screen.ui.DeatilScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.HoritonalPagerScreen.route
    ){
        composable(route = AuthScreen.LoginScreen.route){
//            loginScreen(
//                onClick = {
//                    navController.popBackStack()
//                    navController.navigate(Graph.HOME)
//                },
//            )
            LoginScreen(navController = navController)
        }
        composable(route = AuthScreen.SignUpScreen.route){
            SignUpScreen(navController)
        }
        composable(route = AuthScreen.ForgetPasswordScreen.route){
            ForgetScreen(navController)
        }
        composable(route = AuthScreen.HoritonalPagerScreen.route){
            DeatilScreen(navController = navController)
        }
    }
}