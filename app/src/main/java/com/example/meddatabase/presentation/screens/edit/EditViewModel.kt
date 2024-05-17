package com.example.meddatabase.presentation.screens.edit

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

class EditViewModel (private val authRepo: AuthRepo,private val repo: MedRepo) : ViewModel() {
    var selectedInfo : MedInfo? = null

    var medName by mutableStateOf("")
    var details by mutableStateOf("")
    var formattedDate by mutableStateOf("")

    fun medNameIsValid():Boolean{
        return medName.isNotBlank()
    }

    fun dateIsValid():Boolean{
        return formattedDate != ""
    }

    fun startupEdit(thing: MedInfo?){
        medName= thing?.medName.toString()
        details = thing?.details.toString()
        formattedDate = thing?.formattedDate.toString()
    }


    fun updateMedInfo(thing: MedInfo?){
        selectedInfo = thing!!
        selectedInfo!!.medName = medName
        selectedInfo!!.details = details
        selectedInfo!!.formattedDate = formattedDate
        repo.edit(selectedInfo!!, authRepo.currentUser!!.uid)
    }

    fun deleteMedInfo(thing: MedInfo?){
        selectedInfo = thing!!
        repo.delete(selectedInfo!!, authRepo.currentUser!!.uid)
    }

    companion object {
        val Factory: ViewModelProvider.Factory= viewModelFactory() {
            initializer {
                EditViewModel(
                    authRepo = MedApplication.container.authRepository,
                    repo = MedApplication.container.medRepository
                )
            }
        }
    }

}