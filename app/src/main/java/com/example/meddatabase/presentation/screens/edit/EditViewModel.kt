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
import com.example.meddatabase.data.interfaces.Repo
import com.example.meddatabase.data.interfaces.UpdateMedListener
import com.example.meddatabase.data.medinfo.MedInfo
import com.example.meddatabase.presentation.screens.view_delete.HomeViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel (private val repo: Repo<MedInfo>) : ViewModel() {
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

    // from home view model
/*    private val _contactState = MutableStateFlow(DatabaseState<MedInfo>())

    fun setSelectedUser(user: FirebaseUser?, indexInt: Int){
        val updateContactListener =  repo as UpdateMedListener
        updateContactListener.updateUserListener(MedApplication.container.returnContextForDatabaseListener(user))
        getMedInfo(indexInt)
    }

    private fun getMedInfo(selectedIndex: Int) = viewModelScope.launch {
        repo.getAll().collect { result ->
            when(result) {
                is DatabaseResult.Success -> {
                    _contactState.update { it.copy(data = result.data) }
                    selectedInfo = _contactState.value.data[selectedIndex]
                    Log.v("yo",result.toString())
                }
                is DatabaseResult.Error -> {
                    _contactState.update {
                        it.copy(errorMessage = result.exception.message!!)
                    }
                }
                is DatabaseResult.Loading -> {
                    _contactState.update { it.copy(isLoading = true) }
                }

                else -> {}
            }
        }
    }*/

    fun updateMedInfo(thing: MedInfo?){
        selectedInfo = thing!!
        selectedInfo!!.medName = medName
        selectedInfo!!.details = details
        repo.edit(selectedInfo!!)
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
                    repo = MedApplication.container.medRepository
                )
            }
        }
    }

}