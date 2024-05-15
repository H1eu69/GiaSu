package com.projectprovip.h1eu.giasu.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormat {
    fun DD_MM_YYYY_ISO(date: String): String {
        return try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val dateTime = LocalDateTime.parse(date, formatter)
            dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        } catch (e: DateTimeParseException) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dateTime = LocalDateTime.parse(date, formatter)
            dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        }
    }
}

fun String.alphaNumericOnly(): String {
    val regex = Regex("[^A-Za-z0-9 ]")
    return regex.replace(this, "")
}

fun String.isEmailFormatted(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    return emailRegex.matches(this)
}

fun String.isPhoneNumber(): Boolean {
    val digitRegex = Regex("\\d+")
    return this.length <= 10 && digitRegex.matches(this)
}

fun String.isUsername(): Boolean {
    val usernameRegex = Regex("^[a-zA-Z0-9_]{3,50}$")
    return usernameRegex.matches(this)
}

fun String.isPasswordFormatted(): Boolean {
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

fun String.toEDSIntGender(): Int {
    return when (this) {
        "Male" -> 1
        "Female" -> 2
        else -> 3
    }
}

fun String.toEDSIntLearningMode(): Int {
    return when (this) {
        "Offline" -> 1
        "Online" -> 2
        else -> 3
    }
}

fun String.toEDSIntAcademicLevel(): Int {
    return when (this) {
        "Ungraduated" -> 1
        "Graduated" -> 2
        else -> 3
    }
}

fun Int.toEDSStringGender(): String {
    return when (this) {
        1 -> "Male"
        2 -> "Female"
        else -> "Other"
    }
}

fun Int.toEDSStringLearningMode(): String {
    return when (this) {
        1 -> "Offline"
        2 -> "Online"
        else -> "Other"
    }
}

fun Int.toEDSStringAcademicLevel(): String {
    return when (this) {
        1 -> "Ungraduated"
        2 -> "Graduated"
        else -> "Other"
    }
}

fun String.isLink(): Boolean {
    val regex = Regex(
        "(http://|https://)?[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+(\\S*)?"
    )
    return regex.matches(this)
}

fun Double.keepFirstDecimalDouble(): Double {
    return (this * 10.0).toInt() / 10.0
}