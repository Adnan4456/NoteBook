package com.example.notebook.feature_login.presentation.ui

import java.util.Objects

data class RegistertrationFormState(
    val email: String = "",
    val emailError: Boolean = false,
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordError: Boolean = false,
    val passwordErrorMessage: String? = null,
)
