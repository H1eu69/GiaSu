package com.projectprovip.h1eu.giasu.di

import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.data.bank.api.BankDeeplinkApi
import com.projectprovip.h1eu.giasu.data.bank.repository.BankDeeplinkRepositoryImpl
import com.projectprovip.h1eu.giasu.data.course.api.CourseApi
import com.projectprovip.h1eu.giasu.data.course.api.RecommendCourseApi
import com.projectprovip.h1eu.giasu.data.course.repository.CourseRepositoryImpl
import com.projectprovip.h1eu.giasu.data.course.repository.RecommendCoursesRepositoryImpl
import com.projectprovip.h1eu.giasu.data.location.api.LocationApi
import com.projectprovip.h1eu.giasu.data.location.repository.LocationRepositoryImpl
import com.projectprovip.h1eu.giasu.data.profile.api.ProfileApi
import com.projectprovip.h1eu.giasu.data.profile.repository.ProfileRepositoryImpl
import com.projectprovip.h1eu.giasu.data.subject.api.SubjectApi
import com.projectprovip.h1eu.giasu.data.subject.repository.SubjectRepositoryImpl
import com.projectprovip.h1eu.giasu.data.tutor.api.TutorApi
import com.projectprovip.h1eu.giasu.data.tutor.repository.TutorRepositoryImpl
import com.projectprovip.h1eu.giasu.data.user.api.UserAuthApi
import com.projectprovip.h1eu.giasu.data.user.repository.UserRepositoryImpl
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
import com.projectprovip.h1eu.giasu.domain.bank_deeplink.repository.BankDeeplinkRepository
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import com.projectprovip.h1eu.giasu.domain.course.repository.RecommendCourseRepository
import com.projectprovip.h1eu.giasu.domain.location.repository.LocationRepository
import com.projectprovip.h1eu.giasu.domain.profile.repository.ProfileRepository
import com.projectprovip.h1eu.giasu.domain.subject.repository.SubjectRepository
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserAuthApi(): UserAuthApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClassesApi(): CourseApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTutorApi(): TutorApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TutorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBankApi(): BankDeeplinkApi {
        return Retrofit.Builder()
            .baseUrl(Constant.DEEPLINK_BANK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BankDeeplinkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationApi(): LocationApi {
        return Retrofit.Builder()
            .baseUrl(Constant.LOCATION_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileApi(): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSubjectApi(): SubjectApi {
        return Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SubjectApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecommendCourseApi(): RecommendCourseApi {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS) // 30 seconds connection timeout
            .readTimeout(15, TimeUnit.SECONDS)    // 30 seconds read timeout
            .writeTimeout(15, TimeUnit.SECONDS)   // 30 seconds write timeout
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.RECOMMEND_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecommendCourseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserAuthApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideClassesRepository(api: CourseApi): CoursesRepository {
        return CourseRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideTutorsRepository(api: TutorApi): TutorRepository {
        return TutorRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProvinceRepository(api: LocationApi): LocationRepository {
        return LocationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSubjectRepository(api: SubjectApi): SubjectRepository {
        return SubjectRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRecommendCoursesRepository(api: RecommendCourseApi): RecommendCourseRepository {
        return RecommendCoursesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideBankDeeplinkRepository(api: BankDeeplinkApi): BankDeeplinkRepository {
        return BankDeeplinkRepositoryImpl(api)
    }
}