package com.projectprovip.h1eu.giasu.data.bank.repository

import com.projectprovip.h1eu.giasu.data.bank.api.BankDeeplinkApi
import com.projectprovip.h1eu.giasu.data.bank.dto.BankDeeplinkDto
import com.projectprovip.h1eu.giasu.domain.bank_deeplink.repository.BankDeeplinkRepository
import javax.inject.Inject

class BankDeeplinkRepositoryImpl @Inject constructor(
    private val api: BankDeeplinkApi
) : BankDeeplinkRepository {
    override suspend fun getBankDeepLinks(): BankDeeplinkDto {
        return api.getDeeplinkBanks()
    }
}