package com.example.notebook.feature_login.domain.repository

interface EmailValidator {
    fun isValid(email: String): Boolean
}