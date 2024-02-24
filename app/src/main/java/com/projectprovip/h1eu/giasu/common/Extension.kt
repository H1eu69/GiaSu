package com.projectprovip.h1eu.giasu.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormat {
    fun DD_MM_YYYY(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val dateTime = LocalDateTime.parse(date)

        return dateTime.format(formatter)
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