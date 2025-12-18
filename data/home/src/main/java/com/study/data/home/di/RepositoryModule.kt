package com.study.data.home.di

import com.study.data.home.datasource.remote.datasource.AIChatDataSource
import com.study.data.home.repository.AIChatRepositoryImpl
import com.study.data.home.repository.CategoryFlashcardRepositoryImpl
import com.study.data.home.repository.FlashCardRepositoryImpl
import com.study.data.home.repository.QuizRepositoryImpl
import com.study.domain.home.repository.TaskItemRepository
import com.study.data.home.repository.TaskItemRepositoryImpl
import com.study.domain.home.repository.AIChatRepository
import com.study.domain.home.repository.CategoryFlashcardRepository
import com.study.domain.home.repository.FlashCardRepository
import com.study.domain.home.repository.QuizzRepository
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


    @Singleton
    @Provides
    fun provideQuizRepository(
        aiChatDataSource: AIChatDataSource,
    ): QuizzRepository {
        return QuizRepositoryImpl(
            aiChatDataSource
        )
    }


    @Singleton
    @Provides
    fun provideAIChatRepository(
        aiChatDataSource: AIChatDataSource,
    ): AIChatRepository {
        return AIChatRepositoryImpl(
            aiChatDataSource
        )
    }

}
