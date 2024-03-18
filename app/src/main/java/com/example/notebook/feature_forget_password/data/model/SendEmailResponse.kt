package com.example.notebook.feature_forget_password.data.model


sealed class SendEmailResponse {

    object  initialState : SendEmailResponse()
    object isLoading : SendEmailResponse()
    data class isSuccessfull(val status: Boolean): SendEmailResponse()
    data class onFailure(val errorMessage: String?):SendEmailResponse()

}