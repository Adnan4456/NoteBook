package com.example.notebook.feature_note.presentation.bookmarked_notes


import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.notebook.feature_note.presentation.bookmarked_notes.ui.BookMarkedViewModel


@Composable
fun BookMarkedScreen(
    viewModel: BookMarkedViewModel = hiltViewModel()
){

    val state by viewModel.noteList.collectAsState()

    when{
        state.isLoading -> {
            CircularProgressIndicator()
        }
    }


}