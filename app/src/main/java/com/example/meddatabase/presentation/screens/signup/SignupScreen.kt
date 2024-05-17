package com.example.meddatabase.presentation.screens.signup


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meddatabase.presentation.components.CustomTextField
import com.example.meddatabase.R
import com.example.meddatabase.presentation.components.CustomButton
import com.example.meddatabase.presentation.components.SmallSpacer
import com.example.meddatabase.presentation.navigation.NavScreen
import com.example.meddatabase.presentation.screens.signup.components.SignUp
import com.example.meddatabase.presentation.utils.Util.Companion.showMessage

@Composable
fun SignupScreen(vm: SignupViewModel = viewModel(factory =
SignupViewModel.Factory),
                 navigateBack: () -> Unit) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Enter details to sign up")
                CustomTextField(
                    stringResource(R.string.email),
                    text = vm.email,
                    isPasswordField = false,
                    onValueChange = { vm.email = it },
                    errorPresent = vm.emailIsValid(),
                    stringResource(R.string.email_error_message)
                )
                SmallSpacer()
                CustomTextField(
                    stringResource(R.string.password),
                    text = vm.password,
                    isPasswordField = true,
                    onValueChange = { vm.password = it },
                    errorPresent = vm.passwordIsValid(),
                    stringResource(R.string.email_error_message)

                )
                SmallSpacer()
                CustomButton(
                    stringResource(R.string.submit_button),
                    clickButton = {
                        keyboard?.hide()
                        vm.signUpWithEmailAndPassword()
                        Toast.makeText(context,
                            context.getString(R.string.signup_confirm),
                            Toast.LENGTH_LONG).show();
                    },
                    null
                )
                Row {
                    CustomButton(
                        stringResource(R.string.back_button),
                        clickButton = {
                            navigateBack()
                        },
                        null
                    )
                }
            }
        }
    )

    SignUp(
        vm = vm,
        sendEmailVerification = {
            vm.sendEmailVerification()
        },
        showVerifyEmailMessage = {
            showMessage(context, "Confirm details via email")
        },
        showFailureToSignUpMessage = {
            showMessage(context, "Unable to create sign up due to permissions")
        }
    )
}