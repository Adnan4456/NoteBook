package com.example.notebook.feature_forget_password.presentation

data class ForgetPasswordState(
    val email: String = "",
    val emailError: Boolean = false,
    val emailErrorMessage: String? = null,
)