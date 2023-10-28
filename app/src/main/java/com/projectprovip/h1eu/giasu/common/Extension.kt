package com.projectprovip.h1eu.giasu.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormat {
    fun DD_MM_YYYY(date: String): String {

        val inputDate = "2023-06-22T21:29:19"
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val dateTime = LocalDateTime.parse(inputDate)

        return dateTime.format(formatter)
    }
}