package com.example.notebook



import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.notebook.feature_note.presentation.util.BottomBarScreen
import com.example.notebook.navigation.BottomNavGraph
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    firbaseAuth: FirebaseAuth
){

    val navController  = rememberNavController()

    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) Color.Black else Color.White
        )
    }

    Scaffold (
        Modifier.
        background(
//            colorResource(id = R.color.background_color)
        color = Color.White
        ),

        bottomBar = {
            NavigationBar(navController)
        }
            ){innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            BottomNavGraph(navController = navController , firbaseAuth =firbaseAuth )
        }
    }
}

@Composable
fun NavigationBar(navController: NavHostController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screenList = listOf(
        BottomBarScreen.NotesScreen,
        BottomBarScreen.BookMarkedScreen,
    )

    val bottomBarDestination = screenList.any { it.route == currentDestination?.route }

    if(bottomBarDestination){
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
            screenList.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController)
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            screen.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Navigation Icon"
                )
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,

        onClick = {
            navController.navigate(screen.route) {
                popUpTo(
                    navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}