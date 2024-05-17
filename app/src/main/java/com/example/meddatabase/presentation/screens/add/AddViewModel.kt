package com.example.meddatabase.presentation.screens.add


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meddatabase.core.MedApplication
import com.example.meddatabase.data.auth.AuthRepo
import com.example.meddatabase.data.medinfo.MedInfo
import com.example.meddatabase.data.medinfo.MedRepo

class AddViewModel (private val authRepo: AuthRepo, private val repo: MedRepo) : ViewModel() {
    var medName by mutableStateOf("")
    var details by mutableStateOf("")
    var startDate by mutableStateOf(0)
    var endDate by mutableStateOf(0)

    fun medNameIsValid():Boolean{
        return medName.isNotBlank()
    }
    fun dateIsValid():Boolean{
        return startDate > 0
    }


    fun addMedication(){
        if(medNameIsValid() && dateIsValid()) {
            var newMedInfo = MedInfo(
                medName,
                details,
                startDate,
                endDate
            )
            repo.add(newMedInfo, authRepo.currentUser!!.uid)
            clear()
        }
    }

    private fun clear(){
        medName =""
        details=""
        startDate=0
        endDate=0
    }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AddViewModel(
                    authRepo = MedApplication.container.authRepository,
                    repo = MedApplication.container.medRepository
                )
            }
        }

    }

}