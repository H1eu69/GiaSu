package com.projectprovip.h1eu.giasu.domain.classes.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.classes.dto.ClassInformationDto
import com.projectprovip.h1eu.giasu.data.classes.dto.toNewClass
import com.projectprovip.h1eu.giasu.domain.classes.model.NewClass
import com.projectprovip.h1eu.giasu.domain.classes.repository.ClassesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetClassUseCase @Inject constructor(
    private val repository: ClassesRepository
){
    operator fun invoke(pageIndex: Int, subjectName: String?) = flow<Result<List<NewClass>>> {
        try {
            emit(Result.Loading())
            val data = repository.getAllClasses(pageIndex, subjectName).value.map {
                it.toNewClass()
            }
            emit(Result.Success(data))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Result.Error(e.localizedMessage))
        }
    }
}