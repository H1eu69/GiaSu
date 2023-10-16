package com.projectprovip.h1eu.giasu.domain.authentication.usecase

import com.projectprovip.h1eu.giasu.common.Resource
import com.projectprovip.h1eu.giasu.data.remote.dto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.remote.model.UserLoginInput
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(userLoginInput: UserLoginInput) : Flow<Resource<UserLoginDto>> = flow {
        try {
            emit(Resource.Loading())
            val user = userRepository.login(userLoginInput)
            emit(Resource.Success(user))
        }
        catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage))
        }
    }
}