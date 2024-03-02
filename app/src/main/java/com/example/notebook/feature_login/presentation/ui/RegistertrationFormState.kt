package com.example.notebook.feature_login.presentation.ui

import java.util.Objects

data class RegistertrationFormState(
    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null
)
