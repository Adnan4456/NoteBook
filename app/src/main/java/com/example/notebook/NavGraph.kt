package com.example.notebook

import android.util.Log
import androidx.compose.runtime.*
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
import com.example.notebook.feature_secret_note.presentation.component.PasswordDialog
import com.example.notebook.feature_secret_note.presentation.ui.SecretNotes
import com.example.notebook.feature_signup.presentation.ui.SignUpScreen
import com.example.notebook.feature_verify_user.presentation.ui.VerificationScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun NavGraph(
    startDestination: String,
    firbaseAuth: FirebaseAuth,
    navController : NavHostController
){

    var showDialog by remember { mutableStateOf(false) }

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
            BookMarkedScreen(navController = navController)
        }

//        composable(route = Screen.VerificationScreen.route){
//
//
//        }

        composable(route = Screen.SecretNotes.route){

            VerificationScreen(navController = navController)
//            if (firbaseAuth.currentUser?.email != null ){
//                PasswordDialog(navController,
//                onSuccess = {
//                    Log.d("TAG","Event is called onsucess")
//                })
//            }
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
                noteColor = color
            )
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