package com.example.notebook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.notebook.navigation.RootNavigationGraph
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


        setContent {

            val window = rememberWindowSizeClass()

            NoteBookTheme(window) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val systemUiController = rememberSystemUiController()
                    val darkTheme = isSystemInDarkTheme()

                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = if (darkTheme) Color.Black else Color.White
                        )
                    }
                    val navController  = rememberNavController()
                    RootNavigationGraph(navController = navController ,firebaseAuth = firbaseAuth)
                }
            }
        }
    }
}


