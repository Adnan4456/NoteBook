package com.example.notebook.feature_forget_password.domain.repository

import com.example.notebook.feature_forget_password.data.model.SendEmailResponse

interface ForgetPasswordRepository {

    suspend fun  sendPasswordResetEmail(email: String):SendEmailResponse

}