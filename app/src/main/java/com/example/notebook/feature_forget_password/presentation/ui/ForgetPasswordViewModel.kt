package com.example.notebook.feature_forget_password.presentation.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_forget_password.data.model.SendEmailResponse
import com.example.notebook.feature_forget_password.data.repository.ForgetPasswordRepositoryImpl
import com.example.notebook.feature_forget_password.presentation.ForgetPasswordEvent
import com.example.notebook.feature_forget_password.presentation.ForgetPasswordState
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel
    @Inject
    constructor(
        private val emailAndPasswordUseCase: EmailAndPasswordUseCase,
        private val forgetPasswordRepositoryImpl: ForgetPasswordRepositoryImpl
    ) :ViewModel() {

    var state by mutableStateOf(ForgetPasswordState())


    private val _responseState = MutableStateFlow<SendEmailResponse>(SendEmailResponse.initialState)
    var responseState = _responseState.asStateFlow()

    fun onEvent(event : ForgetPasswordEvent){
        when(event){

            is ForgetPasswordEvent.EmailChanged ->{
                Log.d("TAG","EmailChanged called ")
                state = state.copy(
                    email = event.email
                )
            }

            is ForgetPasswordEvent.submit ->{
                validateEmail()
            }
        }
    }

    private fun validateEmail(){
        val emailResult = emailAndPasswordUseCase.emailUseCase.invoke(state.email)

        state = state.copy(
            emailErrorMessage = emailResult.errorMessage,
            emailError = !emailResult.successful
        )
        if (emailResult.successful){
            sendEmail(state.email)
        }
    }
    private fun sendEmail(email:String){

        viewModelScope.launch {

            val result = forgetPasswordRepositoryImpl.sendPasswordResetEmail(email)

            _responseState.value = SendEmailResponse.isLoading

            when(result){

                is  SendEmailResponse.isSuccessfull -> {
                    _responseState.value = SendEmailResponse.isSuccessfull(true)
                }

                is SendEmailResponse.onFailure -> {
                    _responseState.value = SendEmailResponse.onFailure("Error in send mail.Try again")
                }
                is SendEmailResponse.initialState -> {
                }
                is SendEmailResponse.isLoading -> {
                    _responseState.value = SendEmailResponse.isLoading
                }
            }
        }
    }
}