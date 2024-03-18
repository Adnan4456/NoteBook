package com.example.notebook.feature_login.data.repository

import com.example.notebook.feature_login.data.model.LoginResponse
import com.example.notebook.feature_login.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl
    @Inject constructor(
        private val firebaseAuth : FirebaseAuth
    )
    :LoginRepository {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): LoginResponse {
        return withContext(Dispatchers.IO){
            try {
              val result =  firebaseAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener{}
                   .addOnFailureListener{

                   }.await()
               LoginResponse.isSuccessful(result.user != null)
           }catch (e: Exception){
               LoginResponse.onFailure(e.localizedMessage)
           }

        }
    }
}