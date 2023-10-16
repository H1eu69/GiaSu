package com.projectprovip.h1eu.giasu.common

sealed class Resource<T>(val data : T? = null, val message: String? = null) {
    class Success<T>(data: T?, message: String? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null, message: String? = null) : Resource<T>(data)
    class Error<T>(message: String?) : Resource<T>(message = message)
}