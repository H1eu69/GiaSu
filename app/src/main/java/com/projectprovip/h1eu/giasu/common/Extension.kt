package com.projectprovip.h1eu.giasu.common

import android.content.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormat {
    fun DD_MM_YYYY(date: String): String {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX")
        val dateTime = LocalDateTime.parse(date, formatter)
        val formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return formattedDateTime

    }
}

fun String.alphaNumericOnly() : String {
    val regex = Regex("[^A-Za-z0-9 ]")
    return regex.replace(this, "")
}

fun String.isEmailFormatted() : Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    return emailRegex.matches(this)
}