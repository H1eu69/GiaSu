package com.projectprovip.h1eu.giasu.domain.authentication.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.UserToken
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
    operator fun invoke(userLoginInput: UserLoginInput) : Flow<EDSResult<UserToken>> = flow {
        try {
            emit(EDSResult.Loading())
            val user = userRepository.login(userLoginInput)
            emit(EDSResult.Success(user.value))
        }
        catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
        catch (e: IOException){
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}