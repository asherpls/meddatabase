package com.example.meddatabase.presentation.screens.signup.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.meddatabase.data.Response
import com.example.meddatabase.presentation.components.ProgressBar
import com.example.meddatabase.presentation.screens.signup.SignupViewModel

@Composable
fun SignUp(vm: SignupViewModel,
           sendEmailVerification: () -> Unit,
           showVerifyEmailMessage: () -> Unit,
           showFailureToSignUpMessage: () -> Unit
) {
    when(val signUpResponse = vm.signUpResponse) {
        is Response.Loading, Response.Startup -> ProgressBar()
        is Response.Success -> {
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                }
            }
        }
        is Response.Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showFailureToSignUpMessage()
            }
        }
    }
}