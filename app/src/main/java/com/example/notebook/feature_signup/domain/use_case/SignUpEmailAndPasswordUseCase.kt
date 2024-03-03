package com.example.notebook.feature_signup.domain.use_case

import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_signup.data.model.SignUpResponse
import com.example.notebook.feature_signup.domain.model.SignUpResult
import com.example.notebook.feature_signup.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpEmailAndPasswordUseCase
    @Inject constructor(
        private val signUpRepository: SignUpRepository
    ) {

    suspend  operator fun invoke(email : String, password : String) : SignUpResult{
        val repositoryResult = signUpRepository.singupEmailAndPassword(email, password)
        return when(repositoryResult){
            is SignUpResponse.isSuccessful -> {
                SignUpResult.isSuccessful(true)
            }
            is SignUpResponse.onFailure ->{
                SignUpResult.onFailure("SignUp failed. Please try again")
            }
        }
    }
}