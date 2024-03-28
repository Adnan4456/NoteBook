package com.example.notebook.feature_login.data.repository

import com.example.notebook.feature_login.data.model.LoginResponse
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class LoginRepositoryImplTest{

     @Mock
     lateinit var firebaseAuth : FirebaseAuth
     private lateinit var loginRepository: LoginRepositoryImpl

     @Before
     fun setup(){
         MockitoAnnotations.openMocks(this)
         loginRepository = LoginRepositoryImpl(firebaseAuth)
     }

    @Test
    fun testLoginStatusSuccessfull() = runBlocking{
//        val mockAuthResult: AuthResult = mock(AuthResult::class.java)
//        val mockTask: Task<AuthResult> = Mockito.mock(Task::class.java) as Task<AuthResult>
//        `when`(mockTask.isSuccessful).thenReturn(true) // Indicate success
//        `when`(mockTask.result).thenReturn(mockAuthResult) // Provide mock AuthResult

        // Mock the behavior of signInWithEmailAndPassword
//        `when`(firebaseAuth.signInWithEmailAndPassword("test@example.com", "password"))
//            .thenReturn(Task<AuthResult>.isSuccessful())

//        val expected = LoginResponse.isSuccessful(true)
//
//        val result: LoginResponse = loginRepository.loginWithEmailAndPassword("test@example.com", "password")

//        assertEquals(expected,result)
    }
 }