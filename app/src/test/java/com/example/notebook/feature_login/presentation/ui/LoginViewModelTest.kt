package com.example.notebook.feature_login.presentation.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notebook.feature_internet_connectivity.data.NetworkConnectivityObserver
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_login.domain.model.ValidationResult
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import com.example.notebook.feature_login.domain.use_case.LoginEmailAndPasswordUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class LoginViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = StandardTestDispatcher()


    @Mock
     lateinit var  emailAndPasswordUseCase: EmailAndPasswordUseCase

    @Mock
     lateinit var networkConnectivityObserver: NetworkConnectivityObserver

    @Mock
     lateinit var loginEmailAndPasswordUseCase: LoginEmailAndPasswordUseCase

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp(){
        Dispatchers.setMain(testCoroutineDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(emailAndPasswordUseCase, networkConnectivityObserver, loginEmailAndPasswordUseCase)


        `when`(emailAndPasswordUseCase.emailUseCase.invoke("test@example.com")).thenReturn(
            ValidationResult(true)
        )

        `when`(emailAndPasswordUseCase.passwordUseCase.invoke("password")).thenReturn(
            ValidationResult(true))
    }

    @Test
    fun `test email and password validation`() {


        viewModel.onEvent(LoginFormEvent.EmailChanged("test@example.com"))
        viewModel.onEvent(LoginFormEvent.PasswordChanged("password"))

        viewModel.onEvent(LoginFormEvent.Submit)

        assertEquals(true, viewModel.allValidationPassed)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}