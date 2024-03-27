package com.projectprovip.h1eu.giasu.domain.profile.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.profile.dto.updateProfileDto.UpdateProfileDto
import com.projectprovip.h1eu.giasu.domain.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(token: String, params: UpdateProfileParams) =
        flow<EDSResult<UpdateProfileDto>> {
            emit(EDSResult.Loading())
            try {
                val profile = profileRepository.updateProfile(token, params)
                if (profile.isSuccess)
                    emit(EDSResult.Success(profile))
                else
                    emit(EDSResult.Error(profile.error.description))
            } catch (e: HttpException) {
                emit(EDSResult.Error(e.localizedMessage))
            } catch (e: IOException) {
                emit(EDSResult.Error(e.localizedMessage))
            }
        }
}

data class UpdateProfileParams(
    var avatar: String,
    val birthYear: Int,
    val city: String,
    val country: String,
    val description: String,
    val email: String,
    val firstName: String,
    val gender: Int,
    val lastName: String,
    val phoneNumber: String
)