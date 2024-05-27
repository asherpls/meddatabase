package com.example.meddatabase.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.meddatabase.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDatePicker(
    state: DatePickerState,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        DatePicker(
            state = state,
            modifier = Modifier
                .weight(1f)
                .padding(20.dp)
                .semantics { R.string.date_hint},
            title = {
                Text(text = stringResource(R.string.date_hint))
            },
        )
    }
}