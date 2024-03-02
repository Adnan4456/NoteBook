package com.example.notebook.feature_login.data.repository

import com.example.notebook.feature_login.domain.repository.PasswordValidator
import javax.inject.Inject

class DefaultPasswordValidator
    @Inject constructor()
    : PasswordValidator {

    override fun isLengthValid(password: String): Boolean {

        return password.length >=8
    }

    override fun containsLetterAndDigits(password: String): Boolean {
        return  password.any{it.isDigit() } && password.any {it.isLetter()}
    }
}