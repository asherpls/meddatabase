package com.example.meddatabase.presentation.components

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDatePicker(
    state: DateRangePickerState,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        DateRangePicker(state = state, modifier = Modifier.weight(1f),  title = {
            Text(text = "Duration of Medicine use")
        },)
    }
}