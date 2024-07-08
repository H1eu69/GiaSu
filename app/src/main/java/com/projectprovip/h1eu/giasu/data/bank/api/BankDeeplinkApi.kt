package com.projectprovip.h1eu.giasu.data.bank.api

import com.projectprovip.h1eu.giasu.data.bank.dto.BankDeeplinkDto
import retrofit2.http.GET

interface BankDeeplinkApi {
    @GET("android-app-deeplinks")
    suspend fun getDeeplinkBanks(): BankDeeplinkDto
}