package com.example.notebook.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notebook.feature_note.presentation.add_edit_note.ui.MainScreentesting
import com.example.notebook.feature_note.presentation.bookmarked_notes.BookMarkedScreen
import com.example.notebook.feature_note.presentation.notes.ui.NotesScreen
import com.example.notebook.feature_note.presentation.util.BottomBarScreen
import com.example.notebook.feature_secret_note.presentation.ui.SecretNotes
import com.example.notebook.feature_todo.presentation.edit_todo.ui.AddTodoScreen
import com.example.notebook.feature_verify_user.presentation.ui.VerificationScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    firbaseAuth: FirebaseAuth
    ) {

    var verified by remember {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.NotesScreen.route
    ) {

        composable(route = BottomBarScreen.NotesScreen.route) {
          NotesScreen(navController = navController, firbaseAuth = firbaseAuth )
        }

        composable(
            route = BottomBarScreen.AddEditNoteScreen.route +
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
//            AddEditNoteScreen(
//                navController = navController,
//                noteColor = color
//            )
            MainScreentesting(
                navController = navController,
                noteColor = color
            )
        }


        composable(route = BottomBarScreen.BookMarkedScreen.route){
            BookMarkedScreen(navController = navController)
//            MainScreentesting()
        }

        composable(route = BottomBarScreen.AddTodoScreen.route){
            AddTodoScreen()
        }

        composable(route = BottomBarScreen.SecretNotes.route){

            if(verified){
                SecretNotes(navController)
            }else{
                VerificationScreen(
                    onCompleteListener = {
                        verified = true
                    }
                )
            }
        }

    }
}