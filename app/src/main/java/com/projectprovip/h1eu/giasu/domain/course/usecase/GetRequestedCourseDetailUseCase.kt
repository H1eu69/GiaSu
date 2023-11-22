package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.course.dto.toRequestedCourseDetail
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRequestedCourseDetailUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(id: Int, token: String) = flow<Result<RequestedCourseDetail>> {
        try {
            emit(Result.Loading())
            val response = coursesRepository.getRequestedCourseDetail(id, token)
            if(response.body() != null) {
                emit(Result.Success(response.body()!!.requestedCourseDetail.toRequestedCourseDetail()))
                Log.d("GetRequested", response.body()!!.requestedCourseDetail.toRequestedCourseDetail().toString())
            } else if(!response.message().isNullOrEmpty()) {
                emit(Result.Error(response.message()))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Result.Error(e.localizedMessage))
        }

    }
}