package com.example.notebook.feature_login.presentation.ui


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_internet_connectivity.data.NetworkConnectivityObserver
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import com.example.notebook.feature_login.domain.use_case.LoginEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailAndPasswordUseCase: EmailAndPasswordUseCase,
    private val networkConnectivityObserver: NetworkConnectivityObserver,
    private val loginEmailAndPasswordUseCase: LoginEmailAndPasswordUseCase
) :ViewModel() {

    var state by mutableStateOf(RegistertrationFormState())
    var networkState by mutableStateOf(ConnectivityObserver.Status.Available)

    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus = _networkStatus

    var allValidationPassed by mutableStateOf(true)

    private val _loginState = MutableStateFlow<LoginResult>(LoginResult.isLoading)
    val loginState: StateFlow<LoginResult> get() = _loginState

    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe().onEach {status ->
                networkState = status
            }
        }

        observeNetworkStatus()

    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { status ->
                _networkStatus.value = status
            }
        }
    }
    fun onEvent(event: RegistrationFormEvent){
        when (event){

            is RegistrationFormEvent.EmailChanged ->{
                state = state.copy(
                    email = event.email
                )
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password
                )
            }
            is RegistrationFormEvent.Submit ->{
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = emailAndPasswordUseCase.emailUseCase.invoke(state.email)
        val passwordResult = emailAndPasswordUseCase.passwordUseCase.invoke(state.password)

        state = state.copy(
            emailError = !emailResult.successful,
            emailErrorMessage = emailResult.errorMessage,
            passwordError = !passwordResult.successful,
            passwordErrorMessage = passwordResult.errorMessage
        )

        allValidationPassed = emailResult.successful && passwordResult.successful
        if (allValidationPassed){
            //now called loginWith email and password repository
            loginEmailAndPassword()
        }
    }

    private fun loginEmailAndPassword(){
        viewModelScope.launch {

            try {

                _loginState.value = LoginResult.isLoading

                when(loginEmailAndPasswordUseCase.invoke(state.email,state.password)){
                    is LoginResult.isLoading ->{
                        _loginState.value = LoginResult.isLoading
                    }
                    is LoginResult.isSuccessful ->{
                        _loginState.value = LoginResult.isSuccessful(true)
                    }
                    is LoginResult.onFailure ->{

                        _loginState.value = LoginResult.onFailure("Failed to login. Try again")
                    }

                }

            }catch (_:Exception){

            }


        }
    }
}