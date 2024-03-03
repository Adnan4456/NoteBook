package com.example.notebook.feature_signup.presentation.ui

sealed class RegistrationFormEvent{

    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}
