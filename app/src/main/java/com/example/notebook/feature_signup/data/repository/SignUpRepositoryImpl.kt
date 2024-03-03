package com.example.notebook.feature_signup.data.repository

import com.example.notebook.feature_signup.data.model.SignUpResponse
import com.example.notebook.feature_signup.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpRepositoryImpl
@Inject constructor(
    private val firebaseAuth : FirebaseAuth
)
    : SignUpRepository {

    override suspend fun singupEmailAndPassword(email: String, password: String): SignUpResponse {
       return withContext(Dispatchers.IO){
           try {

               val result =  firebaseAuth.createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener{
//                       LoginResponse.isSuccessful(it.isSuccessful)
                   }
                   .addOnFailureListener{

                   }.await()
               SignUpResponse.isSuccessful(result.user != null)

           }catch (e:Exception){
               SignUpResponse.onFailure(e.localizedMessage)
           }
       }
    }
}