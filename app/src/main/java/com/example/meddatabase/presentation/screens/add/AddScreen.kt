package com.example.meddatabase.presentation.screens.add


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meddatabase.R
import com.example.meddatabase.presentation.components.BottomSheetDatePicker
import com.example.meddatabase.presentation.components.CustomButton
import com.example.meddatabase.presentation.components.CustomTextField
import com.example.meddatabase.presentation.components.SmallSpacer
import com.example.meddatabase.presentation.components.TopAppBarComp
import java.time.Instant

//vm: AddViewModel = viewModel(factory = AddViewModel.Factory),

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(vm: AddViewModel = viewModel(factory = AddViewModel.Factory),onClickToHome: () -> Unit){
    val keyboardController = LocalSoftwareKeyboardController.current

    // date picker stuff
    val datePickerState = rememberDateRangePickerState(yearRange = 2024..2025)
    var bottomSheetState by remember{ mutableStateOf(false) }

    TopAppBarComp(barText = "Add", onClickHome = onClickToHome)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Spacer(Modifier.size(70.dp))
        CustomTextField(
            stringResource(R.string.medication_hint),
            text = vm.medName,
            false,
            onValueChange = { vm.medName = it },
            vm.medNameIsValid(),
            stringResource(R.string.medication_error),

        )
        SmallSpacer()
        CustomTextField(
            stringResource(R.string.details_hint),
            text = vm.details,
            false,
            onValueChange = { vm.details = it },
            vm.medNameIsValid(),
            stringResource(R.string.medication_error),
            )
        SmallSpacer()
        Button(
            onClick = {
                bottomSheetState = true
            }
        ) {
            androidx.compose.material3.Text(text = if (datePickerState.selectedStartDateMillis!= null) {
                "date selected"
            } else {
                "Set medicine date range"
            })
        }
        if (bottomSheetState) {
            BottomSheetDatePicker(
                state = datePickerState,
                onDismissRequest = { bottomSheetState = false }
            )
            vm.startDate = (datePickerState.selectedStartDateMillis ?: 0).toInt()
            vm.endDate = (datePickerState.selectedEndDateMillis ?: 0).toInt()
        }
        Column {
            CustomButton(stringResource(R.string.add),
                clickButton = {
                    vm.addMedication()
                    keyboardController?.hide()
                    onClickToHome()}, null)
        }
    }
}
