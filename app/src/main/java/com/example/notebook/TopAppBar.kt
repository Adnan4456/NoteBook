package com.example.notebook


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


enum class TopAppBarAction {
    Search, Sort, Logout
}

@Composable
fun NotesAppBar(
    title: String,
    actions: List<TopAppBarAction>
) {
   TopAppBar(
       backgroundColor = Color.White,
       elevation = 1.dp,
       title = {
        Text(text = title)
       },
       actions = {

           actions.onEach {action ->
               when (action){
                  TopAppBarAction.Search ->{

                      Card(
                          backgroundColor = Color.White,
                          elevation = 10.dp,
                          shape = RoundedCornerShape(5.dp)
                      ){
                          IconButton(onClick = {
//                            viewModel.onEvent(NotesEvent.ToggleOrderSection)
                          },
                          ) {
                              Icon(
                                  imageVector = Icons.Default.Search,
                                  contentDescription = "Sort Note"
                              )
                          }
                      }
                      IconButton(onClick = {
//                          viewModel.onEvent(NotesEvent.ToggleOrderSection)
                      },
                      ) {
                          Icon(
                              imageVector = Icons.Default.Sort,
                              contentDescription = "Sort Note"
                          )
                      }

                  }
                   TopAppBarAction.Logout ->{

                   }
                   TopAppBarAction.Sort ->{

                   }
               }
           }
       }
   )
}
