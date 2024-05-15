package com.example.meddatabase.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FloatingButton(contentDescription: String,
                   clickAction: () -> Unit,
                   modifier: Modifier = Modifier) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ){
        FloatingActionButton(onClick = clickAction) {
            Icon(Icons.Filled.Add, contentDescription = contentDescription)
        }
    }
}