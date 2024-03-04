package com.example.notebook

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notebook.feature_login.presentation.ui.LoginScreen
import com.example.notebook.feature_note.presentation.add_edit_note.ui.AddEditNoteScreen
import com.example.notebook.feature_note.presentation.bookmarked_notes.BookMarkedScreen
import com.example.notebook.feature_note.presentation.notes.ui.NotesScreen
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.feature_signup.presentation.ui.SignUpScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun NavGraph(
    startDestination: String,
    firbaseAuth: FirebaseAuth,
    navController : NavHostController
){

    NavHost(
        navController = navController,
        startDestination = startDestination) {

        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }

        composable(route = Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.NotesScreen.route){
            NotesScreen(navController = navController ,firbaseAuth = firbaseAuth )
        }
        composable(route = Screen.BookMarkedScreen.route){
            BookMarkedScreen()
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
            val color = it.arguments?.getInt("noteColor") ?: -1
            AddEditNoteScreen(
                navController = navController,
                noteColor = color)
        }
    }
}
fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}