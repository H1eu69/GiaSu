package com.projectprovip.h1eu.giasu.domain.authentication.usecase

import com.projectprovip.h1eu.giasu.common.Resource
import com.projectprovip.h1eu.giasu.data.remote.dto.UserSignUpDto
import com.projectprovip.h1eu.giasu.data.remote.model.UserSignUpInput
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userSignUpInput: UserSignUpInput) : Flow<Resource<UserSignUpDto>> = flow {
        try {
            emit(Resource.Loading())
            val data = userRepository.register(userSignUpInput)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}