package com.example.notebook.feature_note.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Security
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val title: String,
    val route: String,
    val icon: ImageVector? = null
){
//    object BottomBarScreen: BottomBarScreen("main_screen")
    object NotesScreen: BottomBarScreen("notes_screen" ,"notes_screen", icon = Icons.Default.Home)
    object AddEditNoteScreen: BottomBarScreen("add_edit_notes_screen" , "add_edit_notes_screen")
    object BookMarkedScreen:BottomBarScreen("bookmark" , "bookmark",icon = Icons.Default.Bookmark)
    object SecretNotes : BottomBarScreen("secret_notes" , "secret_notes",icon = Icons.Outlined.Security)
    object VerificationScreen:BottomBarScreen("verification_screen","verification_screen")
    object AddTodoScreen:BottomBarScreen("add_todo_screen","add_todo_screen")
    object  Test:BottomBarScreen("testing" , "testing")
}

sealed class AuthScreen(val route:String){
    object LoginScreen:AuthScreen("login_screen")

    object SignUpScreen:AuthScreen("sign_up_screen")

    object HoritonalPagerScreen : AuthScreen("horitonal_screen")

    object ForgetPasswordScreen:AuthScreen("forget_password_screen")


}
