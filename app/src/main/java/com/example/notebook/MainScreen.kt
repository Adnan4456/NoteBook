package com.example.notebook

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.rememberNavController
import com.example.notebook.feature_note.presentation.util.Screen
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
                        Spacer(modifier = Modifier.Companion.size(12.dp))

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
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navController.navigateToSingleTop(
                            route = Screen.AddEditNoteScreen.route
                        )
                    }) {
                        Icon(imageVector =Icons.Default.Add , contentDescription = "Add")
                    }
                }
            )
        }
            ){

        NavGraph(startDestination = startDestination, firbaseAuth =firbaseAuth  , navController)
    }
}

enum class TabScreen{
    Home , BookMark
}