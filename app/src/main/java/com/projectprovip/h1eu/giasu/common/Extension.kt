package com.projectprovip.h1eu.giasu.common

import java.text.SimpleDateFormat
import java.util.Locale
object DateFormat {
    fun DD_MM_YYYY(date: String) : String {
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault())
        val date = inputFormat.parse(date)

        return outputFormat.format(date!!)
    }
}