package com.example.notebook.feature_login.domain.use_case

import android.content.Context
import com.example.notebook.R
import com.example.notebook.feature_login.domain.model.ValidationResult
import com.example.notebook.feature_login.domain.repository.EmailValidator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValideteEmailUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val emailValidator: EmailValidator
) {

    operator fun invoke(email:String)  : ValidationResult {

        if(email.isBlank()){

            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.blank_email),
            )
        }
        if(!emailValidator.isValid(email)){
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.invalide_email)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}