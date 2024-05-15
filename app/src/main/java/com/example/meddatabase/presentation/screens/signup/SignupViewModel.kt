package com.example.meddatabase.presentation.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meddatabase.core.MedApplication
import com.example.meddatabase.data.Response
import com.example.meddatabase.data.auth.AuthRepo
import com.example.meddatabase.data.auth.SendEmailVerificationResponse
import kotlinx.coroutines.launch

class SignupViewModel (private val repo: AuthRepo) : ViewModel() {
    var email by mutableStateOf(String())
    var password by mutableStateOf(String())
    fun emailIsValid():Boolean{
        return email.isNotBlank()
    }
    fun passwordIsValid():Boolean{
        return password.isNotBlank()
    }
    var signUpResponse by
    mutableStateOf<Response<Boolean>>(Response.Success(false))
        private set
    private var sendEmailVerificationResponse by
    mutableStateOf<SendEmailVerificationResponse>(
        Response.Success(
            false
        )
    )

    fun signUpWithEmailAndPassword() = viewModelScope.launch {
        signUpResponse = Response.Loading
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email,
            password)
    }
    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Response.Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignupViewModel(repo =
                MedApplication.container.authRepository)
            }
        }
    }
}
