package com.projectprovip.h1eu.giasu.domain.profile.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.profile.dto.toProfile
import com.projectprovip.h1eu.giasu.domain.profile.model.Profile
import com.projectprovip.h1eu.giasu.domain.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(token: String) = flow<EDSResult<Profile>>{
        emit(EDSResult.Loading())
        try {
            val profile = profileRepository.getUserProfile(token).value.toProfile()
            emit(EDSResult.Success(profile))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}