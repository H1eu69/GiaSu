package com.projectprovip.h1eu.giasu.domain.bank_deeplink.repository

import com.projectprovip.h1eu.giasu.data.bank.dto.BankDeeplinkDto

interface BankDeeplinkRepository {
    suspend fun getBankDeepLinks() : BankDeeplinkDto
}