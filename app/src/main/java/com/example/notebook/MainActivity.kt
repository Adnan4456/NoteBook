package com.example.notebook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController

import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.ui.theme.NoteBookTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
            Screen.HoritonalPagerScreen.route
        }

        setContent {

            val window = rememberWindowSizeClass()

            NoteBookTheme(window) {
                startScreen(firbaseAuth , startDestination,)
            }
        }
    }
}

@Composable
fun startScreen(firbaseAuth: FirebaseAuth, startDestination: String,) {
    val navController  = rememberNavController()

    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) Color.Black else Color.White
        )
    }

    if(startDestination == Screen.HoritonalPagerScreen.route){
        NavGraph(startDestination = startDestination, firbaseAuth =firbaseAuth  , navController)
    }
    else{
        MainScreen(startDestination =  startDestination, firbaseAuth = firbaseAuth)
    }


}

