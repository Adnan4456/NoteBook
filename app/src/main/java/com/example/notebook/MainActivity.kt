package com.example.notebook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.feature_login.presentation.ui.LoginViewModel

import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.splash_activity.ui.DeatilScreen
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


        installSplashScreen()

        val user = firbaseAuth.currentUser

        val startDestination = if (user != null){
            Screen.NotesScreen.route
        }else
        {
            Screen.HoritonalScreen.route
        }

        setContent {

            val window = rememberWindowSizeClass()

            NoteBookTheme(window) {

                MainScreen(startDestination =  startDestination, firbaseAuth = firbaseAuth)

            }
        }
    }
}

