package com.example.jetfilesapplication

import kotlin.Result

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Loading<out T>(val data: T?) : Result<Nothing>()
    data class Error(val exception: Exception) : Result<Nothing>()
}