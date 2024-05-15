package com.example.meddatabase.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meddatabase.core.MedApplication
import com.example.meddatabase.data.Response
import com.example.meddatabase.data.auth.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel (private val repo: AuthRepo) : ViewModel() {
    var email by mutableStateOf(String())
    var password by mutableStateOf(String())
    //revisit
    val isEmailVerified  = true

    var signInResponse by
    mutableStateOf<Response<Boolean>>(Response.Startup)
        private set
    private var _message = MutableLiveData(String())
    var message: LiveData<String> = _message
    fun emailIsValid():Boolean{
        return email.isNotBlank()
    }
    fun passwordIsValid():Boolean{
        return password.isNotBlank()
    }
    fun forgotPassword() {
        FirebaseAuth.getInstance()
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _message.value = "Password reset email has been sent successfully"
                } else {
                    _message.value = "Unable to send password reset email"
                }
            }
    }
    fun signInWithEmailAndPassword() = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(repo =
                MedApplication.container.authRepository)
            }
        }
    }
}
