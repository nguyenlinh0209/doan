package com.study.domain.user.di

import com.study.domain.user.repository.UserRepository
import com.study.domain.user.usecase.GetCurrentUserEmail
import com.study.domain.user.usecase.GetCurrentUserUseCase
import com.study.domain.user.usecase.GetUserByEmailUseCase
import com.study.domain.user.usecase.GetUserById
import com.study.domain.user.usecase.LoginUseCase
import com.study.domain.user.usecase.SignOutUseCase
import com.study.domain.user.usecase.UpdatePasswordUseCase
import com.study.domain.user.usecase.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideGetUserByEmailUseCase(
        userRepository: UserRepository,
    ): GetUserByEmailUseCase {
        return GetUserByEmailUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentUserEmail(
        userRepository: UserRepository,
    ): GetCurrentUserEmail {
        return GetCurrentUserEmail(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentUserUseCase(
        userRepository: UserRepository,
    ): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideSingoutUseCase(
        userRepository: UserRepository,
    ): SignOutUseCase {
        return SignOutUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetUserById(
        userRepository: UserRepository,
    ): GetUserById {
        return GetUserById(userRepository)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(
        userRepository: UserRepository,
    ): LoginUseCase {
        return LoginUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateUserUseCase(
        userRepository: UserRepository,
    ): UpdateUserUseCase {
        return UpdateUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideUpdatePasswordUseCase(
        userRepository: UserRepository,
    ): UpdatePasswordUseCase {
        return UpdatePasswordUseCase(userRepository)
    }
}


