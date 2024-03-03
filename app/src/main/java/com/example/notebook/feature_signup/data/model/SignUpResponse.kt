package com.example.notebook.feature_signup.data.model

sealed class SignUpResponse{

    data class isSuccessful(val status: Boolean): SignUpResponse()
    data class onFailure(val errorMessage: String?): SignUpResponse()
}