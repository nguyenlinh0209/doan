package com.osprey.intro.di

import com.study.common.navigation.IntroNavigator
import com.study.intro.navigation.IntroNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IntroNavigationModule {
    @Provides
    @Singleton
    fun provideIntroNavigator(): IntroNavigator = IntroNavigatorImpl()
}