package com.projectprovip.h1eu.giasu.di

import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.data.course.api.CourseApi
import com.projectprovip.h1eu.giasu.data.course.repository.CourseRepositoryImpl
import com.projectprovip.h1eu.giasu.data.location.api.province.ProvinceApi
import com.projectprovip.h1eu.giasu.data.location.repository.ProvinceRepositoryImpl
import com.projectprovip.h1eu.giasu.data.tutor.api.TutorApi
import com.projectprovip.h1eu.giasu.data.tutor.repository.TutorRepositoryImpl
import com.projectprovip.h1eu.giasu.data.user.api.UserAuthApi
import com.projectprovip.h1eu.giasu.data.user.repository.UserRepositoryImpl
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import com.projectprovip.h1eu.giasu.domain.location.repository.ProvinceRepository
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserAuthApi() : UserAuthApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClassesApi() : CourseApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTutorApi() : TutorApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TutorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationApi() : ProvinceApi {
        return Retrofit.Builder()
            .baseUrl(Constant.LOCATION_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProvinceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api : UserAuthApi) : UserRepository{
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideClassesRepository(api : CourseApi) : CoursesRepository {
        return CourseRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideTutorsRepository(api : TutorApi) : TutorRepository {
        return TutorRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProvinceRepository(api : ProvinceApi) : ProvinceRepository {
        return ProvinceRepositoryImpl(api)
    }
}