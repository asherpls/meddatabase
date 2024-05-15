package com.example.meddatabase.data


data class DatabaseState<T>(
    val data: List<T?> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String=""
)