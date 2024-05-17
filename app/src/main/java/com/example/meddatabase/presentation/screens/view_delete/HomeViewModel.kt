package com.example.meddatabase.presentation.screens.view_delete

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel (private val authRepo: AuthRepo, private val repo: MedRepo) : ViewModel() {
    var selectedMed : MedInfo? = null
    private val _contactState = MutableStateFlow(DatabaseState<MedInfo>())
    val contactState: StateFlow<DatabaseState<MedInfo>> = _contactState.asStateFlow()//Monitored by component for recomposition on change

    init {
        getMedInfo(authRepo.currentUser!!.uid)
    }
    fun contactHasBeenSelected(): Boolean = selectedMed!=null


    private fun getMedInfo(userId: String) = viewModelScope.launch {
        repo.getAll(userId).collect { result ->
            when(result) {
                is DatabaseResult.Success -> {
                    _contactState.update { it.copy(data = result.data) }
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
                    authRepo = MedApplication.container.authRepository,
                    repo = MedApplication.container.medRepository
                )
            }
        }
    }
}