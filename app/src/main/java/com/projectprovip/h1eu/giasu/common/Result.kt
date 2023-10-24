package com.projectprovip.h1eu.giasu.common

sealed class Result<T>(val data : T? = null, val message: String? = null) {
    class Success<T>(data: T?, message: String? = null) : Result<T>(data)
    class Loading<T>(data: T? = null, message: String? = null) : Result<T>(data)
    class Error<T>(message: String?) : Result<T>(message = message)
}