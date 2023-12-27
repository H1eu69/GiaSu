package com.projectprovip.h1eu.giasu.common

sealed class EDSResult<T>(val data : T? = null, val message: String? = null) {
    class Success<T>(data: T?, message: String? = null) : EDSResult<T>(data)
    class Loading<T>(data: T? = null, message: String? = null) : EDSResult<T>(data)
    class Error<T>(message: String?) : EDSResult<T>(message = message)
}