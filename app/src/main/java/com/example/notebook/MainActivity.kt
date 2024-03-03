package com.example.notebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notebook.feature_login.presentation.ui.LoginScreen
import com.example.notebook.feature_note.presentation.add_edit_note.ui.AddEditNoteScreen
import com.example.notebook.feature_note.presentation.notes.ui.NotesScreen
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.feature_signup.presentation.ui.SignUpScreen
import com.example.notebook.ui.theme.AppTheme
import com.example.notebook.ui.theme.NoteBookTheme
import com.example.notebook.ui.theme.Orientation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContent {

            val window = rememberWindowSizeClass()
            NoteBookTheme(window) {

                    Surface(
                        color = MaterialTheme.colors.background
                    ) {

                        val navController  = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.LoginScreen.route) {

                            composable(route = Screen.LoginScreen.route){

                                LoginScreen(navController = navController)
                            }

                            composable(route = Screen.SignUpScreen.route){
                               SignUpScreen(navController = navController)
                            }
                            composable(route = Screen.NotesScreen.route){
                                NotesScreen(navController = navController)
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


            }
        }
    }
}