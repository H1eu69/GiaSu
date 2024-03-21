package com.projectprovip.h1eu.giasu.domain.location.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.location.dto.province.toProvinceItem
import com.projectprovip.h1eu.giasu.domain.location.model.Province
import com.projectprovip.h1eu.giasu.domain.location.repository.ProvinceRepository
import com.projectprovip.h1eu.giasu.presentation.authentication.model.ProvinceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProvinceUseCase @Inject constructor(
    private val provinceRepository: ProvinceRepository
) {
   operator fun invoke ()  : Flow<EDSResult<List<ProvinceItem>>> = flow {
       emit(EDSResult.Loading())
       try {
           val province = provinceRepository.getProvince().results.map {
               it.toProvinceItem()
           }
           emit(EDSResult.Success(province))
       } catch (e: HttpException) {
           emit(EDSResult.Error(e.localizedMessage))
       } catch (e: IOException) {
           emit(EDSResult.Error(e.localizedMessage))
       }
   }
}