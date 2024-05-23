package com.projectprovip.h1eu.giasu.presentation.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Instants {
    fun getVietQrImage(
        bankName: String = "vietinbank",
        bankNumber: String = "107867236970",
        template: String = "print",
        amount: String = "10000",
        addInfo: String? = null,
        accountName: String = "Huỳnh Trung Hiếu"
    ): String {
        return "https://img.vietqr.io/image/$bankName-$bankNumber-$template.jpg?amount=$amount&addInfo=$addInfo&accountName=$accountName"
    }
}

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