package com.example.meddatabase.presentation.screens.edit

import android.R.attr.data
import android.app.PendingIntent.getActivity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meddatabase.R
import com.example.meddatabase.data.medinfo.MedInfo
import com.example.meddatabase.presentation.components.CustomButton
import com.example.meddatabase.presentation.components.CustomTextField
import com.example.meddatabase.presentation.components.SmallSpacer
import com.example.meddatabase.presentation.components.TopAppBarComp
import com.google.firebase.auth.FirebaseAuth


// vm: EditViewModel = viewModel(factory = EditViewModel.Factory)
@Composable
fun EditScreen(
    selectedContactIndex: MedInfo?,
    onClickToHome: () -> Unit,
    vm: EditViewModel = viewModel(factory = EditViewModel.Factory),
){
    val context = LocalContext.current.applicationContext

    TopAppBarComp(barText = "Edit", onClickHome = onClickToHome)

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
        Column {
            CustomButton(
                stringResource(R.string.edit),
                clickButton = {if(vm.medNameIsValid()){
                    vm.updateMedInfo(selectedContactIndex)
                    onClickToHome()
                }else{
                    Toast.makeText(context,
                        context.getString(R.string.no_selection),
                        Toast.LENGTH_LONG).show();
                    Log.v("nah","buster")
                }}, null)
            CustomButton(
                stringResource(R.string.delete),
                clickButton = {
                    vm.deleteMedInfo(selectedContactIndex)
                    onClickToHome()
                    Toast.makeText(context,
                        context.getString(R.string.deleteConfirm),
                        Toast.LENGTH_LONG).show();
                    Log.v("nah","buster")
                }, null)
        }
    }
}