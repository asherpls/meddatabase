package com.example.meddatabase.presentation.screens.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meddatabase.R
import com.example.meddatabase.data.medinfo.MedInfo
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
fun EditScreen(
    selectedContactIndex: MedInfo?,
    onClickToHome: () -> Unit,
    vm: EditViewModel = viewModel(factory = EditViewModel.Factory),
){
    // date picker stuff
    val datePickerState = rememberDatePickerState(yearRange = 2024..2025)
    var bottomSheetState by remember{ mutableStateOf(false) }

    val dateText = Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    val context = LocalContext.current.applicationContext

    TopAppBarComp(barText = "Edit", onClickHome = onClickToHome, infoText = stringResource(R.string.edit_hint))

    LaunchedEffect(key1 = Unit) {//Called on launch
        vm.startupEdit(selectedContactIndex)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.size(70.dp))
        CustomTextField(
            stringResource(R.string.medication_hint),
            text = vm.medName ,
            false,
            onValueChange = { vm.medName = it },
            vm.medNameIsValid(),
            stringResource(R.string.medication_error),
            )
        SmallSpacer()
        CustomTextField(
            stringResource(R.string.details_hint),
            text = vm.details ,
            false,
            onValueChange = { vm.details = it },
            vm.medNameIsValid(),
            stringResource(R.string.medication_error),
        )
        Button(
            onClick = {
                bottomSheetState = true
            },
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = if (datePickerState.selectedDateMillis!= null) {
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
            CustomButton(
                stringResource(R.string.edit),
                clickButton = {if(CheckValid(vm= vm, context)){
                    vm.updateMedInfo(selectedContactIndex)
                    onClickToHome()
                }},Icons.Filled.Edit)
            CustomButton(
                stringResource(R.string.delete),
                clickButton = {
                    vm.deleteMedInfo(selectedContactIndex)
                    onClickToHome()
                    Toast.makeText(context,
                        context.getString(R.string.deleteConfirm),
                        Toast.LENGTH_LONG).show();
                }, null)
        }
    }
}


fun CheckValid(
    vm: EditViewModel,
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
            "Medicine edited",
            Toast.LENGTH_LONG).show();
        true
    }
}