package com.example.meddatabase.presentation.screens.view_delete

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meddatabase.core.MedApplication
import com.example.meddatabase.data.DatabaseResult
import com.example.meddatabase.data.DatabaseState
import com.example.meddatabase.data.interfaces.Repo
import com.example.meddatabase.data.interfaces.UpdateMedListener
import com.example.meddatabase.data.medinfo.MedInfo
import com.example.meddatabase.data.user.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel (private val repo: Repo<MedInfo>) : ViewModel() {
    var selectedContact : MedInfo? = null
    private val _contactState = MutableStateFlow(DatabaseState<MedInfo>())
    val contactState: StateFlow<DatabaseState<MedInfo>> = _contactState.asStateFlow()//Monitored by component for recomposition on change

    fun contactHasBeenSelected(): Boolean = contactState!=null

    fun setSelectedUser(user: FirebaseUser?){
        val updateContactListener =  repo as UpdateMedListener
        updateContactListener.updateUserListener(MedApplication.container.returnContextForDatabaseListener(user))
        getMedInfo()
    }

    private fun getMedInfo() = viewModelScope.launch {
        repo.getAll().collect { result ->
            when(result) {
                is DatabaseResult.Success -> {
                    _contactState.update { it.copy(data = result.data) }
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
    }
    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory= viewModelFactory() {
            initializer {
                HomeViewModel(
                    repo = MedApplication.container.medRepository
                )
            }
        }
    }
}