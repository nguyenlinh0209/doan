package com.osprey.data.user.di

import com.osprey.data.common.datasource.AppSharePrefs
import com.osprey.data.user.datasource.remote.interceptor.AuthDataSource
import com.osprey.data.user.repository.UserRepositoryImpl
import com.study.domain.user.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        authDataSource: AuthDataSource,
        appSharePrefs: AppSharePrefs
    ): UserRepository {
        return UserRepositoryImpl(authDataSource, appSharePrefs)
    }
}