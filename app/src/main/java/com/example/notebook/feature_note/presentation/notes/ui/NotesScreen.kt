package com.example.notebook.feature_note.presentation.notes.ui


import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.notes.NotesEvent
import com.example.notebook.feature_note.presentation.notes.NotesState
import com.example.notebook.feature_note.presentation.notes.NotesViewModel
import com.example.notebook.feature_note.presentation.notes.components.*
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    firbaseAuth: FirebaseAuth
) {

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                ToolBar(state , viewModel ,  navController , firbaseAuth)
            }
        ) {innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                SearchBar { query ->
                    viewModel.onSearchQueryChanged(query)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Header(Modifier.padding(0.dp),
                    navController)
            }
        }
    }

}
@Composable
fun ToolBar(
    state: NotesState,
    viewModel: NotesViewModel,
    navController: NavController,
    firbaseAuth: FirebaseAuth
) {
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort Note"
                    )
                }

                Text(
                    text = stringResource(id = R.string.notetitle),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        firbaseAuth.signOut()
                        navController.navigate(route = "auth_graph") {
                            popUpTo("home_graph") {
                                inclusive = false
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Log Out"
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = state.isOrderSectionVisible,
            enter = slideInVertically {

                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.4f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            OrderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                noteOrder = state.noteOrder,
                onOrderChange = {
                    viewModel.onEvent(NotesEvent.Order(it))
                }
            )
        }
    }
}
@Composable
fun NoNotesImage(
){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))

    val repeatableSpec = rememberInfiniteTransition()
        .animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Restart
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        LottieAnimation(
            modifier = Modifier.size(250.dp),
            composition = composition,
            progress = repeatableSpec.value
        )
        Text(text = "Create your  note !")
    }

}