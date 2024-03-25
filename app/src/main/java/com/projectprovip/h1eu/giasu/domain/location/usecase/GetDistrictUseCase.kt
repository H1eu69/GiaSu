package com.projectprovip.h1eu.giasu.domain.location.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.location.dto.district.toDistrict
import com.projectprovip.h1eu.giasu.domain.location.model.District
import com.projectprovip.h1eu.giasu.domain.location.repository.LocationRepository
import com.projectprovip.h1eu.giasu.presentation.authentication.model.ProvinceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDistrictUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke (provinceId: String)  : Flow<EDSResult<List<District>>> = flow {
        emit(EDSResult.Loading())
        try {
            val district = locationRepository.getDistrict(provinceId).results.map {
                it.toDistrict()
            }
            emit(EDSResult.Success(district))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}