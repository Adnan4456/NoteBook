package com.example.notebook.feature_verify_user.presentation



sealed class VerifyUserEvent
{
    data class PasswordChangeEvent(val password: String): VerifyUserEvent()
    object  onSubmit: VerifyUserEvent()
}