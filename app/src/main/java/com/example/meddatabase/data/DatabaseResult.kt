package com.example.meddatabase.data


sealed class DatabaseResult<out Result> {
    data class Success<out T>(val data: T) : DatabaseResult<T>()
    data class Error(val exception: Throwable) : DatabaseResult<Nothing>()
    object Loading : DatabaseResult<Nothing>()
}