package com.example.notebook


import FilterFabMenuItem
import FilterView
import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.notebook.feature_note.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    startDestination: String,
    firbaseAuth: FirebaseAuth
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
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Spacer(modifier = Modifier.Companion.size(8.dp))
                        InputChip(
                            selected = currentTab == TabScreen.Home,
                            onClick = {
                                currentTab = TabScreen.Home
                                navController.navigateToSingleTop(
                                    route = Screen.NotesScreen.route
                                )
                            },
                            label = {
                                Text(text = "Home")
                            },
                            trailingIcon = {
                                Icon(imageVector = Icons.Default.Home, contentDescription = "Home") }
                        )
                        Spacer(modifier = Modifier.Companion.size(8.dp))

                        InputChip(
                            selected = currentTab == TabScreen.BookMark,
                            onClick = {
                                currentTab = TabScreen.BookMark
                                navController.navigateToSingleTop(
                                    route = Screen.BookMarkedScreen.route
                                )
                            },
                            label = {
                                Text(text = "Bookmark")
                            },
                            trailingIcon = {
                                Icon(imageVector = Icons.Default.Bookmark, contentDescription = "BookMark") }
                        )

                        Spacer(modifier = Modifier.Companion.size(8.dp))

                        InputChip(
                            selected = currentTab == TabScreen.SecretNotes,
                            onClick = {
                                currentTab = TabScreen.SecretNotes
                                navController.navigateToSingleTop(
                                    route = Screen.SecretNotes.route
                                )
                            },
                            label = {
                                Text(text = "Private")
                            },
                            trailingIcon = {
                                Icon(imageVector = Icons.Outlined.Security, contentDescription = "Secret_note") }
                        )
                    }
                },

                floatingActionButton = {

                    FloatingActionButton(onClick = {
                        navController.navigateToSingleTop(
                            route = Screen.AddEditNoteScreen.route
                        )
                    }) {
                        Icon(imageVector = Icons.Default.Add , contentDescription = "Add")
                    }
                    FilterView(
                        items = listOf(
                            FilterFabMenuItem("Add Note", R.drawable.add_photo),
                            FilterFabMenuItem("Add Todo", R.drawable.ic_lock)
                        ),
                        modifier = Modifier
                            .padding(16.dp),
                        navController
                    )
                }
            )
        }
            ){innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){

            NavGraph(startDestination = startDestination, firbaseAuth =firbaseAuth  , navController)
        }
    }
}

enum class TabScreen{
    Home , BookMark , SecretNotes
}