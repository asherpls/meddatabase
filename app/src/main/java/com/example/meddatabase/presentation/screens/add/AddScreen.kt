package com.example.meddatabase.presentation.screens.add

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meddatabase.R
import com.example.meddatabase.presentation.components.BottomSheetDatePicker
import com.example.meddatabase.presentation.components.CustomButton
import com.example.meddatabase.presentation.components.CustomTextField
import com.example.meddatabase.presentation.components.SmallSpacer
import com.example.meddatabase.presentation.components.TopAppBarComp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(vm: AddViewModel = viewModel(factory = AddViewModel.Factory),onClickToHome: () -> Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current.applicationContext

    // date picker stuff
    val datePickerState = rememberDatePickerState(yearRange = 2024..2025)
    var bottomSheetState by remember{ mutableStateOf(false) }
    val dateText = Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

    TopAppBarComp(barText = "Add", onClickHome = onClickToHome,infoText = stringResource(R.string.add_hint))

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
            },
            modifier = Modifier
                .padding(10.dp)
        ) {
            androidx.compose.material3.Text(text = if (datePickerState.selectedDateMillis!= null) {
                "Date selected: $dateText"
            } else {
                "Set medicine Expiry Date"
            })
        }
        if (bottomSheetState) {
            BottomSheetDatePicker(
                state = datePickerState,
                onDismissRequest = { bottomSheetState = false }
            )
            vm.formattedDate = dateText
        }
        Column(
            modifier = Modifier
                .padding(12.dp)
                .align(alignment = Alignment.End),
        ) {
            CustomButton(stringResource(R.string.add),
                clickButton = {
                    if(CheckValid(vm= vm, context)){
                    vm.addMedication()
                    keyboardController?.hide()
                    onClickToHome()}}, null)
        }
    }
}

fun CheckValid(
    vm: AddViewModel,
    context: Context
): Boolean {
    return if(!vm.medNameIsValid()) {
        Toast.makeText(context,
            "Medicine name is not valid",
            Toast.LENGTH_LONG).show();
        false
    }
    else if(!vm.dateIsValid()){
        Toast.makeText(context,
            "Medicine date not valid",
            Toast.LENGTH_LONG).show();
        false
    }
    else{
        Toast.makeText(context,
            "Medicine added",
            Toast.LENGTH_LONG).show();
        true
    }
}

