package com.example.notebook.feature_login.data.model

sealed class LoginResponse{

    data class isSuccessful(val status: Boolean): LoginResponse()
    data class onFailure(val errorMessage: String?): LoginResponse()
}