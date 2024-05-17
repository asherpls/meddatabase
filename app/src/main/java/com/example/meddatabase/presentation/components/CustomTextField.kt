package com.example.meddatabase.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    hintText: String,
    text: String,
    isPasswordField: Boolean= false,
    onValueChange: (String) -> Unit,
    errorPresent: Boolean,
    errorMessage: String,
    lines: Int = 1
){
    Surface(modifier = Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            label = {
                Text(hintText)
            },
            maxLines = lines,
            visualTransformation = if (isPasswordField)
                PasswordVisualTransformation('*') else VisualTransformation.None,
        )
        /*Text(
            modifier = Modifier.padding(10.dp),
            text = if (errorPresent) "" else errorMessage,
            fontSize = 14.sp,
            color = Color.Red
        )*/
    }
}