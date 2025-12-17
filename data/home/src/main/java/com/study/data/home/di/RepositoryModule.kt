package com.study.data.home.di

import com.study.data.home.datasource.remote.datasource.AIChatDataSource
import com.study.data.home.repository.CategoryFlashcardRepositoryImpl
import com.study.data.home.repository.FlashCardRepositoryImpl
import com.study.domain.home.repository.TaskItemRepository
import com.study.data.home.repository.TaskItemRepositoryImpl
import com.study.domain.home.repository.CategoryFlashcardRepository
import com.study.domain.home.repository.FlashCardRepository
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
    fun provideTaskItemRepository(
    ): TaskItemRepository {
        return TaskItemRepositoryImpl(
        )
    }

    @Singleton
    @Provides
    fun provideCategoryFlashcardRepository(
    ): CategoryFlashcardRepository {
        return CategoryFlashcardRepositoryImpl(
        )
    }

    @Singleton
    @Provides
    fun provideFlashCardRepository(
      aiChatDataSource: AIChatDataSource,
        ): FlashCardRepository {
        return FlashCardRepositoryImpl(
            aiChatDataSource
        )
    }

}
