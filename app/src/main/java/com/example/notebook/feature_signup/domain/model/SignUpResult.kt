package com.example.notebook.feature_signup.domain.model


sealed class SignUpResult{
    object  initialState : SignUpResult()
    object isLoading : SignUpResult()
    data class isSuccessful(val status : Boolean) : SignUpResult()
    data class onFailure(val message : String?) : SignUpResult()
}
