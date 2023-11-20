package com.projectprovip.h1eu.giasu.domain.course.usecase

import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.course.dto.toNewClassDetail
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCourseUseCase @Inject constructor(
    private val repository: CoursesRepository
){
    operator fun invoke() = flow<Result<List<CourseDetail>>> {
        try {
            emit(Result.Loading())
            val data = repository.getAllClasses().map {
                it.toNewClassDetail()
            }
            emit(Result.Success(data))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Result.Error(e.localizedMessage))
        }
    }
}