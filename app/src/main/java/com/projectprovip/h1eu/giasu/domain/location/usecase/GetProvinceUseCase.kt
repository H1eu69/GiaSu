package com.projectprovip.h1eu.giasu.domain.location.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.location.dto.province.toProvince
import com.projectprovip.h1eu.giasu.domain.location.model.Province
import com.projectprovip.h1eu.giasu.domain.location.repository.LocationRepository
import com.projectprovip.h1eu.giasu.presentation.authentication.model.ProvinceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProvinceUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
   operator fun invoke ()  : Flow<EDSResult<List<Province>>> = flow {
       emit(EDSResult.Loading())
       try {
           val province = locationRepository.getProvince().results.map {
               it.toProvince()
           }
           emit(EDSResult.Success(province))
       } catch (e: HttpException) {
           emit(EDSResult.Error(e.localizedMessage))
       } catch (e: IOException) {
           emit(EDSResult.Error(e.localizedMessage))
       }
   }
}