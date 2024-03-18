package com.example.notebook.feature_forget_password.presentation

sealed class ForgetPasswordEvent {

    data class EmailChanged(val  email: String) : ForgetPasswordEvent()

    object submit: ForgetPasswordEvent()

}