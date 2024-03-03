package com.example.notebook.feature_login.domain.use_case

import com.example.notebook.feature_login.data.model.LoginResponse
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginEmailAndPasswordUseCase
@Inject  constructor(
    private val loginRepository: LoginRepository
){

    suspend operator fun  invoke( email : String, password : String)
    :LoginResult {
        val repositoryResult =loginRepository.loginWithEmailAndPassword(email, password)
        return when(repositoryResult){
            is LoginResponse.isSuccessful -> {
                LoginResult.isSuccessful(true)
            }
            is LoginResponse.onFailure -> {
                LoginResult.onFailure("Login failed. Please try again")
            }
        }
    }
}