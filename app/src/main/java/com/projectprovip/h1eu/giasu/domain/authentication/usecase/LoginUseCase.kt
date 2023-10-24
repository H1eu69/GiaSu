package com.projectprovip.h1eu.giasu.domain.authentication.usecase

import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.user.dto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userLoginInput: UserLoginInput) : Flow<Result<UserLoginDto>> = flow {
        try {
            emit(Result.Loading())
            val user = userRepository.login(userLoginInput)
            emit(Result.Success(user))
        }
        catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage))
        }
        catch (e: IOException){
            emit(Result.Error(e.localizedMessage))
        }
    }
}