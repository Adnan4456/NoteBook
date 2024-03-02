package com.example.notebook.feature_login.domain.use_case

import android.content.Context
import com.example.notebook.R
import com.example.notebook.feature_login.domain.model.ValidationResult
import com.example.notebook.feature_login.domain.repository.PasswordValidator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidetePasswordUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val passwordValidator: PasswordValidator
) {

    operator fun invoke(password:String)  : ValidationResult {

        if(!passwordValidator.isLengthValid(password)){

            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.blank_password),
            )
        }

        if (!passwordValidator.containsLetterAndDigits(password)){
            return ValidationResult(
                successful = false,
                errorMessage = context.getString(R.string.invalide_password)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}