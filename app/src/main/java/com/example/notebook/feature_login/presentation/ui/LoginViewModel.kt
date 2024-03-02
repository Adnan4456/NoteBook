package com.example.notebook.feature_login.presentation.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.nio.channels.Channel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailAndPasswordUseCase: EmailAndPasswordUseCase
) :
    ViewModel() {

    var state by mutableStateOf(RegistertrationFormState())

//    private val valideteChannel: Channel

    fun onEvent(event: RegistrationFormEvent){
        when (event){
            is RegistrationFormEvent.EmailChanged ->{
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.Submit ->{
                submitData()
            }
            else ->{}
        }
    }

    private fun submitData() {
        val emailResult = emailAndPasswordUseCase.emailUseCase.invoke(state.email)
        val passwordResult = emailAndPasswordUseCase.passwordUseCase.invoke(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful   }

        if (hasError){
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError =  passwordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {

        }
    }
}