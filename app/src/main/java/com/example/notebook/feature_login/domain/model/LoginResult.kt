package com.example.notebook.feature_login.domain.model

sealed class LoginResult {
    object isLoading : LoginResult()
    data class isSuccessful(val status : Boolean) : LoginResult()
    data class onFailure(val message : String?) : LoginResult()
}
