package com.example.notebook


import FilterFabMenuItem
import FilterView
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Security

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import javax.inject.Inject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
//    firbaseAuth: FirebaseAuth
){

    val navController  = rememberNavController()
    var currentTab by remember {
        mutableStateOf(TabScreen.Home)
    }

    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) Color.Black else Color.White
        )
    }

    Scaffold (

        Modifier.background(colorResource(id = R.color.background_color)),

        bottomBar = {
            NavigationBar(navController)
//            BottomAppBar(
//                actions = {
//                    Row(horizontalArrangement = Arrangement.Center) {
//                        Spacer(modifier = Modifier.Companion.size(8.dp))
//                        InputChip(
//                            selected = currentTab == TabScreen.Home,
//                            onClick = {
//                                currentTab = TabScreen.Home
//                                navController.navigateToSingleTop(
//                                    route = Screen.NotesScreen.route
//                                )
//                            },
//                            label = {
//                                Text(text = "Home")
//                            },
//                            trailingIcon = {
//                                Icon(imageVector = Icons.Default.Home, contentDescription = "Home") }
//                        )
//                        Spacer(modifier = Modifier.Companion.size(8.dp))
//
//                        InputChip(
//                            selected = currentTab == TabScreen.BookMark,
//                            onClick = {
//                                currentTab = TabScreen.BookMark
//                                navController.navigateToSingleTop(
//                                    route = Screen.BookMarkedScreen.route
//                                )
//                            },
//                            label = {
//                                Text(text = "Bookmark")
//                            },
//                            trailingIcon = {
//                                Icon(imageVector = Icons.Default.Bookmark, contentDescription = "BookMark") }
//                        )
//
//                        Spacer(modifier = Modifier.Companion.size(8.dp))
//
//                        InputChip(
//                            selected = currentTab == TabScreen.SecretNotes,
//                            onClick = {
//                                currentTab = TabScreen.SecretNotes
//                                navController.navigateToSingleTop(
//                                    route = Screen.SecretNotes.route
//                                )
//                            },
//                            label = {
//                                Text(text = "Private")
//                            },
//                            trailingIcon = {
//                                Icon(imageVector = Icons.Outlined.Security, contentDescription = "Secret_note") }
//                        )
//                    }
//                },
//
//                floatingActionButton = {
//
//                    FloatingActionButton(onClick = {
//                        navController.navigateToSingleTop(
//                            route = Screen.AddEditNoteScreen.route
//                        )
//                    }) {
//                        Icon(imageVector = Icons.Default.Add , contentDescription = "Add")
//                    }
//                    FilterView(
//                        items = listOf(
//                            FilterFabMenuItem("Add Note", R.drawable.add_photo),
//                            FilterFabMenuItem("Add Todo", R.drawable.ic_lock)
//                        ),
//                        modifier = Modifier
//                            .padding(16.dp),
//                        navController
//                    )
//                }
//            )
        }
            ){innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){

            BottomNavGraph(navController = navController)
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
        NavigationBar() {
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


enum class TabScreen{
    Home , BookMark , SecretNotes
}