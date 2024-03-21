package com.example.notebook.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notebook.feature_forget_password.presentation.ui.ForgetScreen
import com.example.notebook.feature_login.presentation.ui.LoginScreen
import com.example.notebook.feature_note.presentation.add_edit_note.ui.MainScreentesting
import com.example.notebook.feature_note.presentation.bookmarked_notes.BookMarkedScreen
import com.example.notebook.feature_note.presentation.notes.ui.NotesScreen
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.feature_secret_note.presentation.ui.SecretNotes
import com.example.notebook.feature_signup.presentation.ui.SignUpScreen
import com.example.notebook.feature_splash_screen.ui.DeatilScreen
import com.example.notebook.feature_todo.presentation.edit_todo.ui.AddTodoScreen
 import com.google.firebase.auth.FirebaseAuth


@Composable
fun NestedNavigation(
    navController: NavHostController,
    isLoggedIn: Boolean,
    firebaseAuth: FirebaseAuth
) {
    NavHost(navController = navController, startDestination = "home_graph"){
        auth(navController)
        home(navController , firebaseAuth)
    }
    LaunchedEffect(Unit) {
        if (!isLoggedIn) {
            navController.navigateToSingleTop("auth_graph")
        }
    }
}

fun NavGraphBuilder.auth(
    navController: NavHostController){
    navigation(startDestination = Screen.HoritonalPagerScreen.route ,
    route = "auth_graph"){

        composable(route = Screen.HoritonalPagerScreen.route){

            DeatilScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route, enterTransition = {

            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(2000)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    tween(2000)
                )
            }
        ){
            LoginScreen(navController = navController)
        }

        composable(route = Screen.SignUpScreen.route , enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(2000)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    tween(2000)
                )
            }
        ){
            SignUpScreen(navController = navController)
        }
        composable(route= Screen.ForgetPasswordScreen.route){
            ForgetScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.home(
    navController: NavController,
    firebaseAuth: FirebaseAuth
){
    navigation(
        startDestination = Screen.NotesScreen.route,
        route = "home_graph"
    ){
        composable(route = Screen.NotesScreen.route){
            NotesScreen(navController = navController ,firbaseAuth = firebaseAuth )
//                    NotesTestScreen(navController = navController ,firbaseAuth = firbaseAuth )
        }

        composable(route = Screen.BookMarkedScreen.route){
            BookMarkedScreen(navController = navController)
//            MainScreentesting()
        }

        composable(route = Screen.AddTodoScreen.route){
            AddTodoScreen()
        }

        composable(route = Screen.SecretNotes.route){


            SecretNotes(navController)

        }

        composable(
            route = Screen.AddEditNoteScreen.route +
                    "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ){
//            val color = it.arguments?.getInt("noteColor") ?: -1
//            AddEditNoteScreen(
//                navController = navController,
//                noteColor = color
//            )
            MainScreentesting()
        }
    }
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}