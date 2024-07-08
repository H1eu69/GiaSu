package com.projectprovip.h1eu.giasu.domain.bank_deeplink.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.bank.dto.toBankAppModel
import com.projectprovip.h1eu.giasu.domain.bank_deeplink.model.BankAppModel
import com.projectprovip.h1eu.giasu.domain.bank_deeplink.repository.BankDeeplinkRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBankDeeplinkUseCase @Inject constructor(
    private val bankRepository: BankDeeplinkRepository
) {
    operator fun invoke() = flow<EDSResult<List<BankAppModel>>> {
        try {
            emit(EDSResult.Loading())
            val data = bankRepository.getBankDeepLinks().apps.map {
                it.toBankAppModel()
            }
            Log.d("Get bank deeplink", data.toString())
            emit(EDSResult.Success(data))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}