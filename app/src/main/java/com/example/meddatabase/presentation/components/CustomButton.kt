package com.example.meddatabase.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(text: String, clickButton: () -> Unit, icon: ImageVector?) {
    Button(
        onClick = clickButton,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .testTag("Test".plus(text)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        if (icon != null) {
        Icon(icon, contentDescription = "Edit")}
        Text(text = text, fontSize = 20.sp)
    }
}