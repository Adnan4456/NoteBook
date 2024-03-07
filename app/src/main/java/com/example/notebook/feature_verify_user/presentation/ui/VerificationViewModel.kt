package com.example.notebook.feature_verify_user.presentation.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import com.example.notebook.feature_login.domain.use_case.LoginEmailAndPasswordUseCase
import com.example.notebook.feature_verify_user.presentation.VerifyUserEvent
import com.example.notebook.feature_verify_user.presentation.VerifyUserState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerificationViewModel
    @Inject constructor(
        private val emailAndPasswordUseCase: EmailAndPasswordUseCase,
        private val loginEmailAndPasswordUseCase: LoginEmailAndPasswordUseCase
    ): ViewModel() {


    val emailAddress = FirebaseAuth.getInstance().currentUser?.email
    var email by mutableStateOf(emailAddress)

    var state by mutableStateOf(VerifyUserState())

    private val _verificationState = MutableStateFlow<LoginResult>(LoginResult.initialState)
    val verificationState: StateFlow<LoginResult>
    get() = _verificationState

    fun onEvent(event : VerifyUserEvent){
        when(event){

            is VerifyUserEvent.PasswordChangeEvent -> {
                state = state.copy(
                    password = event.password
                )
            }
            is VerifyUserEvent.onSubmit ->{
                submitData()
            }
        }
    }

    private fun submitData() {
        val isValidPassword = emailAndPasswordUseCase.passwordUseCase.invoke(state.password)

        state = state.copy(
            passwordError = !isValidPassword.successful,
            passwordErrorMessage = isValidPassword.errorMessage
        )
        if(isValidPassword.successful && email?.length!! >0){
            confirmFromServer()
        }
    }

    private fun confirmFromServer() {

        viewModelScope.launch {

            try {

                _verificationState.value = LoginResult.isLoading

                when(loginEmailAndPasswordUseCase.invoke(email!!,state.password)){
                    is LoginResult.isLoading ->{
                        _verificationState.value = LoginResult.isLoading
                    }
                    is LoginResult.isSuccessful ->{
                        _verificationState.value = LoginResult.isSuccessful(true)
                    }
                    is LoginResult.onFailure ->{

                        _verificationState.value = LoginResult.onFailure("Failed to login. Try again")
                    }

                    else -> {}
                }

            }catch (_:Exception){

            }
        }
    }
}