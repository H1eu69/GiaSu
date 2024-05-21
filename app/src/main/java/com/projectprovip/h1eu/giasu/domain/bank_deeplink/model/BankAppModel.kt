package com.projectprovip.h1eu.giasu.domain.bank_deeplink.model

import com.projectprovip.h1eu.giasu.data.bank.dto.App

data class BankAppModel(
    val appId: String,
    val appLogo: String,
    val appName: String,
    val bankName: String,
    val deeplink: String,
    val monthlyInstall: Int
)

