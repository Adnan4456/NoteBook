package com.example.notebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notebook.MainScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun RootNavigationGraph(navController: NavHostController ,
                        firebaseAuth: FirebaseAuth
) {
    val isLoggedIn = firebaseAuth.currentUser != null


    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {

        authNavGraph(navController = navController ,firebaseAuth = firebaseAuth)

//        BottomNavGraph(navController = navController , firbaseAuth =firbaseAuth )

        composable(route = Graph.HOME) {
            MainScreen(firebaseAuth)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}