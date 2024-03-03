package com.example.notebook.feature_signup.presentation.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_internet_connectivity.data.NetworkConnectivityObserver
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import com.example.notebook.feature_signup.domain.model.SignUpResult
import com.example.notebook.feature_signup.domain.use_case.SignUpEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel
    @Inject constructor(
        private val emailAndPasswordUseCase: EmailAndPasswordUseCase,
        private val networkConnectivityObserver: NetworkConnectivityObserver,
        private val signUpEmailAndPasswordUseCase: SignUpEmailAndPasswordUseCase
    ) :ViewModel() {

    var state by mutableStateOf(RegistertrationFormState())

    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)

    var networkState by mutableStateOf(ConnectivityObserver.Status.Available)
    val networkStatus = _networkStatus

    var allValidationPassed by mutableStateOf(true)

    private var _signupState = MutableStateFlow<SignUpResult>(SignUpResult.initialState)

    val signupState: StateFlow<SignUpResult>
        get() = _signupState

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
                allValidationPassed = false
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
            signUpEmailAndPassword()
        }

    }

    private fun signUpEmailAndPassword() {
        viewModelScope.launch{

            try {

                _signupState.value = SignUpResult.initialState
                when(signUpEmailAndPasswordUseCase.invoke(state.email,state.password)){
                    is SignUpResult.isLoading ->{
                        _signupState.value = SignUpResult.isLoading
                    }
                    is SignUpResult.isSuccessful ->{
                        _signupState.value = SignUpResult.isSuccessful(true)
                    }
                    is SignUpResult.onFailure ->{
                        _signupState.value = SignUpResult.onFailure("")
                    }
                    else -> {}
                }
            }catch (_: Exception) {

            }
        }
    }
}