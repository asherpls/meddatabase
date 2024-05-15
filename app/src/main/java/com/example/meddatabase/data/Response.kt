package com.example.meddatabase.data

sealed class Response<out T> {

    object Startup: Response<Nothing>()

    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Failure(
        val e: Exception
    ): Response<Nothing>()
}