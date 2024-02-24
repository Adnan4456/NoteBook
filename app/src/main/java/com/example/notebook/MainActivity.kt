package com.example.notebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notebook.ui.theme.NoteBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var name by remember { mutableStateOf("") }

    Column {
        if (name.isNotEmpty()){
            Text(text = "Hello $name!")
        }
       OutlinedTextField(
           label  = {Text(text = name)},
           value = name ,
           onValueChange = {name  = it}
       )
    }



}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteBookTheme {
        Greeting("Android")
    }
}