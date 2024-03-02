package com.example.notebook.feature_login.data.repository

import android.util.Patterns
import com.example.notebook.feature_login.domain.repository.EmailValidator
import javax.inject.Inject

class DefaultEmailValidator @Inject constructor(
): EmailValidator {
    override fun isValid(email: String): Boolean {
         return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}