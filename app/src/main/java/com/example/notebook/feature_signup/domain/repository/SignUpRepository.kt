package com.example.notebook.feature_signup.domain.repository

import com.example.notebook.feature_signup.data.model.SignUpResponse

interface SignUpRepository {

    suspend fun singupEmailAndPassword(email: String, password: String): SignUpResponse
}