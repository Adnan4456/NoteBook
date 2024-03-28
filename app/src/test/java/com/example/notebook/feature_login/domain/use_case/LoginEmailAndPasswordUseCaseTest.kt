package com.example.notebook.feature_login.domain.use_case

import com.example.notebook.feature_login.data.model.LoginResponse
import com.example.notebook.feature_login.data.repository.LoginRepositoryImpl
import com.example.notebook.feature_login.domain.model.LoginResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class LoginEmailAndPasswordUseCaseTest{

    @Mock
    lateinit var loginRepository: LoginRepositoryImpl

    private lateinit var loginUseCase: LoginEmailAndPasswordUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginUseCase = LoginEmailAndPasswordUseCase(loginRepository)
    }


    @Test
    fun `test login with valid credentials`() = runBlocking {

        `when`(loginRepository.loginWithEmailAndPassword("test@example.com", "password"))
            .thenReturn(LoginResponse.isSuccessful(true))

        val result = loginUseCase("test@example.com", "password")

        assertEquals(LoginResult.isSuccessful(true), result)
    }

    @Test
    fun `test login failed`() = runBlocking {

        `when`(loginRepository.
                loginWithEmailAndPassword("test@example.com", "password"))
            .thenReturn(
                LoginResponse.onFailure("Login failed. Please try again")
            )

        val result = loginUseCase("test@example.com", "password")

        assertEquals(LoginResult.onFailure("Login failed. Please try again"), result)
    }
}