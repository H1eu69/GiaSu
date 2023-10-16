package com.projectprovip.h1eu.giasu.di

import com.projectprovip.h1eu.giasu.common.Constant
import com.projectprovip.h1eu.giasu.data.remote.UserAuthApi
import com.projectprovip.h1eu.giasu.data.repository.UserRepositoryImpl
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
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
    fun provideUserRepository(api : UserAuthApi) : UserRepository{
        return UserRepositoryImpl(api)
    }
}