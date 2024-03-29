package com.projectprovip.h1eu.giasu.domain.profile.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.profile.dto.updateTutorInfoDto.UpdateTutorInfoDto
import com.projectprovip.h1eu.giasu.domain.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class   UpdateTutorInformationUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(token: String, params: UpdateTutorInfoParams) =
        flow<EDSResult<UpdateTutorInfoDto>> {
            emit(EDSResult.Loading())
            try {
                val response = profileRepository.updateTutorInfo(token, params)
                if (response.isSuccess)
                    emit(EDSResult.Success(response))
                else
                    emit(EDSResult.Error(response.error.description))
            } catch (e: HttpException) {
                emit(EDSResult.Error(e.localizedMessage))
            } catch (e: IOException) {
                emit(EDSResult.Error(e.localizedMessage))
            }
        }
}

data class UpdateTutorInfoParams(
    val academicLevel: String,
    val majors: List<Int>,
    val university: String
)