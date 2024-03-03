package com.example.notebook.feature_note.presentation.util

sealed class Screen(
    val route: String,
){
    object NotesScreen: Screen("notes_screen")
    object AddEditNoteScreen: Screen("add_edit_notes_screen")

    object LoginScreen:Screen("login_screen")

    object SignUpScreen:Screen("sign_up_screen")
}
