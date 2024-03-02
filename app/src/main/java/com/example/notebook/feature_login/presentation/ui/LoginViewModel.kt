package com.example.notebook.feature_login.presentation.ui


import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_internet_connectivity.data.NetworkConnectivityObserver
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.nio.channels.Channel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailAndPasswordUseCase: EmailAndPasswordUseCase,
    private val networkConnectivityObserver: NetworkConnectivityObserver
) :ViewModel() {

    var state by mutableStateOf(RegistertrationFormState())
    var networkState by mutableStateOf(ConnectivityObserver.Status.Available)

    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus = _networkStatus




    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe().onEach {status ->
                Log.d("TAG","internet status = ${status}")
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
        viewModelScope.launch {

        }
    }
}