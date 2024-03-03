package com.example.notebook.feature_login.domain.repository

import com.example.notebook.feature_login.data.model.LoginResponse

interface LoginRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String) : LoginResponse

}