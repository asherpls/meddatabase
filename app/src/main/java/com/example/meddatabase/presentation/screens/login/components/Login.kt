package com.example.meddatabase.presentation.screens.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.meddatabase.data.Response
import com.example.meddatabase.presentation.components.ProgressBar
import com.example.meddatabase.presentation.screens.login.LoginViewModel

@Composable
fun LogIn(
    vm: LoginViewModel,
    showErrorMessage: (errorMessage: String?) -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    when(val signInResponse = vm.signInResponse) {
        is Response.Startup -> Unit //Do nothing
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            if (vm.isEmailVerified) {
                LaunchedEffect(key1 = Unit) {
                    navigateToHomeScreen()
                }
            } else {
                showErrorMessage("Email not authorised")
            }
        }

        is Response.Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                showErrorMessage(e.message)
            }
        }
    }}