package com.projectprovip.h1eu.giasu.domain.course.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
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
    operator fun invoke() = flow<EDSResult<List<CourseDetail>>> {
        try {
            emit(EDSResult.Loading())
            val data = repository.getAllClasses().value.map {
                it.toNewClassDetail()
            }
            emit(EDSResult.Success(data))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}