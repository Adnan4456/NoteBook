package com.example.notebook



import FilterFabMenuItem
import FilterView
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    var showFloatingButton by remember {
        mutableStateOf(false)
    }
    val navController  = rememberNavController()

    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) Color.Black else Color.White
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color =  colorResource(id = R.color.all_notes_bg)
    ){
        Scaffold (
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                if (showFloatingButton){
                    Box{

                        FilterView(
                            items = listOf(
                                FilterFabMenuItem("Note", R.drawable.ic_note),
                                FilterFabMenuItem("Todo", R.drawable.ic_todo)
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(y = 50.dp),
                            navController
                        )
                    }
                }
            },
            bottomBar = {
                NavigationBar(
                    navController  ,{
                        showFloatingButton = it
                    } )
            }
        ){innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(
                        color = colorResource(id = R.color.all_notes_bg)
                    )
            ){
                BottomNavGraph(navController = navController , firbaseAuth =firbaseAuth )
            }
        }
    }
}

@Composable
fun NavigationBar(navController: NavHostController, onChange: (Boolean) ->Unit){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screenList = listOf(
        BottomBarScreen.NotesScreen,
        BottomBarScreen.TodoScreen
    )

    val bottomBarDestination = screenList.any { it.route == currentDestination?.route }

    if(bottomBarDestination){
        onChange(true)
        NavigationBar(
            modifier = Modifier
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ),
            containerColor = colorResource(id = R.color.all_notes_bg).copy(.9f),
            tonalElevation = 16.dp
        ) {
            screenList.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController)
            }
        }
    }
    else {
        onChange(false)
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