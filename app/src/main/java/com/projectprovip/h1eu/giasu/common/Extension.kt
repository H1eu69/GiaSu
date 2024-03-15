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
fun String.isPhoneNumber() : Boolean {
    val digitRegex = Regex("\\d+")
    return this.length <= 10 && digitRegex.matches(this)
}
fun String.isUsername() : Boolean {
    val usernameRegex = Regex("^[a-zA-Z0-9_]{3,50}$")
    return usernameRegex.matches(this)
}
fun String.isPasswordFormatted() : Boolean {
    val lowercaseRegex = Regex("[a-z]")
    val uppercaseRegex = Regex("[A-Z]")
    val digitRegex = Regex("[0-9]")
    val specialCharRegex = Regex("[^a-zA-Z0-9]")

    return this.length >= 8 &&
            lowercaseRegex.containsMatchIn(this) &&
            uppercaseRegex.containsMatchIn(this) &&
            digitRegex.containsMatchIn(this) &&
            specialCharRegex.containsMatchIn(this)
}