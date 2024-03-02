package com.example.notebook.feature_login.domain.repository

interface PasswordValidator {
    fun isLengthValid(password: String): Boolean
    fun containsLetterAndDigits(password: String): Boolean
}