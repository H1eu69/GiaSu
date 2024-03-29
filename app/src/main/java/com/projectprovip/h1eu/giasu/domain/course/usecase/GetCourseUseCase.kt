package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.new_courses.toNewCourse
import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCourseUseCase @Inject constructor(
    private val repository: CoursesRepository
){
    operator fun invoke(page: Int, subjectName: String? = null) = flow<EDSResult<List<CourseDetail>>> {
        try {
            emit(EDSResult.Loading())
            val data = repository.getAllClasses(page, subjectName).value.items.map {
                it.toNewCourse()
            }

            Log.d("TEst", data.toString())
            emit(EDSResult.Success(data))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}