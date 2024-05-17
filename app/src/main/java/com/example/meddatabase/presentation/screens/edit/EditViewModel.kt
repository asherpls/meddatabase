package com.example.meddatabase.presentation.screens.edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meddatabase.core.MedApplication
import com.example.meddatabase.data.DatabaseResult
import com.example.meddatabase.data.DatabaseState
import com.example.meddatabase.data.auth.AuthRepo
import com.example.meddatabase.data.medinfo.MedInfo
import com.example.meddatabase.data.medinfo.MedRepo
import com.example.meddatabase.presentation.screens.view_delete.HomeViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel (private val authRepo: AuthRepo,private val repo: MedRepo) : ViewModel() {
    var selectedInfo : MedInfo? = null

    var medName by mutableStateOf("")
    var details by mutableStateOf("")

    fun medNameIsValid():Boolean{
        return medName.isNotBlank()
    }

    fun startupEdit(thing: MedInfo?){
        medName= thing?.medName.toString()
        details = thing?.details.toString()
    }


    fun updateMedInfo(thing: MedInfo?){
        selectedInfo = thing!!
        selectedInfo!!.medName = medName
        selectedInfo!!.details = details
        repo.edit(selectedInfo!!, authRepo.currentUser!!.uid)
    }

    fun deleteMedInfo(thing: MedInfo?){
        selectedInfo = thing!!
        repo.delete(selectedInfo!!)
    }

    // Define ViewModel factory in a companion object
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