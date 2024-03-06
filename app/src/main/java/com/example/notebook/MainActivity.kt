package com.example.notebook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*

import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.ui.theme.NoteBookTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firbaseAuth: FirebaseAuth

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val user = firbaseAuth.currentUser

        val startDestination = if (user != null){
            Screen.NotesScreen.route
        }else
        {
            Screen.LoginScreen.route
        }

        setContent {

            val window = rememberWindowSizeClass()

            NoteBookTheme(window) {
                MainScreen(startDestination = startDestination, firbaseAuth = firbaseAuth)
            }
        }
    }
}

