package com.projectprovip.h1eu.giasu.data.bank.dto

import com.projectprovip.h1eu.giasu.domain.bank_deeplink.model.BankAppModel

data class App(
    val appId: String,
    val appLogo: String,
    val appName: String,
    val bankName: String,
    val deeplink: String,
    val monthlyInstall: Int
)

fun App.toBankAppModel() = BankAppModel(
    appId, appLogo, appName, bankName, deeplink, monthlyInstall
)