package com.example.notebook.feature_forget_password.data.repository

import com.example.notebook.feature_forget_password.data.model.SendEmailResponse
import com.example.notebook.feature_forget_password.domain.repository.ForgetPasswordRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForgetPasswordRepositoryImpl
    @Inject
        constructor(
    private val firebaseAuth : FirebaseAuth
) : ForgetPasswordRepository {

    @Throws(Exception::class)
    override suspend fun sendPasswordResetEmail(email: String): SendEmailResponse {
        return withContext(Dispatchers.IO){
            try {
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener{}
                    .addOnFailureListener{}
                    .await()
                SendEmailResponse.isSuccessfull(true)
            }catch (e: Exception){
                SendEmailResponse.onFailure(e.message)
            }
        }
    }
}