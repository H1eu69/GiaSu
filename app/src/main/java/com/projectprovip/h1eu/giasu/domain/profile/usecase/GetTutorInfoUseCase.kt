package com.projectprovip.h1eu.giasu.domain.profile.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto.toTutorInfo
import com.projectprovip.h1eu.giasu.domain.profile.model.TutorInfo
import com.projectprovip.h1eu.giasu.domain.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTutorInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(token: String) = flow<EDSResult<TutorInfo>>{
        emit(EDSResult.Loading())
        try {
            val profile = profileRepository.getTutorInfo(token).value.toTutorInfo()
            emit(EDSResult.Success(profile))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}