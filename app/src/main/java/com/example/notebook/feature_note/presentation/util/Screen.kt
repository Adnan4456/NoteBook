package com.example.notebook.feature_note.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Security
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector? = null
){
    object NotesScreen: Screen("notes_screen" , icon = Icons.Default.Home)
    object AddEditNoteScreen: Screen("add_edit_notes_screen")

    object LoginScreen:Screen("login_screen")

    object SignUpScreen:Screen("sign_up_screen")

    object BookMarkedScreen:Screen("bookmark" , icon = Icons.Default.Bookmark)

    object SecretNotes : Screen("secret_notes" , icon = Icons.Outlined.Security)

    object VerificationScreen:Screen("verification_screen")

    object AddTodoScreen:Screen("add_todo_screen")
    object  Test:Screen("testing")
}
