package com.projectprovip.h1eu.giasu.domain.location.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.location.dto.district.toDistrict
import com.projectprovip.h1eu.giasu.data.location.dto.ward.toWard
import com.projectprovip.h1eu.giasu.domain.location.model.District
import com.projectprovip.h1eu.giasu.domain.location.model.Ward
import com.projectprovip.h1eu.giasu.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWardUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke (districtId: String)  : Flow<EDSResult<List<Ward>>> = flow {
        emit(EDSResult.Loading())
        try {
            val ward = locationRepository.getWard(districtId).results.map {
                it.toWard()
            }
            emit(EDSResult.Success(ward))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}