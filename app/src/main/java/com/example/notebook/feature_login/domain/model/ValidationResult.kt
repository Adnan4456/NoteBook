package com.example.notebook.feature_login.domain.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage:String? = null,
)
