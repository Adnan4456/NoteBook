package com.example.notebook.feature_note.presentation.notes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notebook.feature_note.presentation.notes.components.CardRow

@Composable
fun Header(navController: NavController) {
    val (selectedCardIndex, setSelectedCardIndex) = remember { mutableStateOf("All Notes") }

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(8.dp))

        CardRow(
           modifier = Modifier.fillMaxWidth()
               .height(50.dp),
            onClick = {
                    index -> setSelectedCardIndex(index)
            })

         when(selectedCardIndex){
            "All Notes" -> AllNotesList(navController)
            "Hidden Notes" -> HiddenNotesList(navController)
            "Favourites"-> FavouritesList(navController)
            "Trash" -> TrashList()
        }
    }
}
