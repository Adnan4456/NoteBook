package com.example.notebook.feature_verify_user.presentation

data class VerifyUserState(
    val password: String = "",
    val passwordError: Boolean = false,
    val passwordErrorMessage: String? = null,
)
