package com.projectprovip.h1eu.giasu.domain.authentication.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.user.dto.UserSignUpDto
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userSignUpInput: UserSignUpInput) : Flow<EDSResult<UserSignUpDto>> = flow {
        try {
            emit(EDSResult.Loading())
            val data = userRepository.register(userSignUpInput)
            emit(EDSResult.Success(data))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}