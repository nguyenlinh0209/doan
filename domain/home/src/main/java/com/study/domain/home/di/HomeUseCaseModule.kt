package com.study.domain.home.di

import com.study.domain.home.repository.AIChatRepository
import com.study.domain.home.repository.CategoryFlashcardRepository
import com.study.domain.home.repository.FlashCardRepository
import com.study.domain.home.repository.QuizzRepository
import com.study.domain.home.repository.TaskItemRepository
import com.study.domain.home.usecase.AsKAIResponseUseCase
import com.study.domain.home.usecase.DeleteTaskUseCase
import com.study.domain.home.usecase.GenerateFlashCardUseCase
import com.study.domain.home.usecase.GenerateQuizUseCase
import com.study.domain.home.usecase.GetAllCategoryFlashCard
import com.study.domain.home.usecase.GetFlashCardByCategoryIDUseCase
import com.study.domain.home.usecase.GetTasksByUserIdUseCase
import com.study.domain.home.usecase.SaveCategoryFlashCard
import com.study.domain.home.usecase.SaveFlashCardByCategoryIDUseCase
import com.study.domain.home.usecase.SaveItemUseCase
import com.study.domain.home.usecase.UpdateTaskDescriptionUseCase
import com.study.domain.home.usecase.UpdateTaskStatusUseCase
import com.study.domain.home.usecase.UpdateTaskTitleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeUseCaseModule {
    @Provides
    @Singleton
    fun provideSaveItemUseCase(
        taskItemRepository: TaskItemRepository,
    ): SaveItemUseCase {
        return SaveItemUseCase(taskItemRepository)
    }

    @Provides
    @Singleton
    fun provideGetTasksByUserIdUseCase(
        taskItemRepository: TaskItemRepository,
    ): GetTasksByUserIdUseCase {
        return GetTasksByUserIdUseCase(taskItemRepository)
    }


    @Provides
    @Singleton
    fun provideUpdateTaskTitleUseCase(
        taskItemRepository: TaskItemRepository,
    ): UpdateTaskTitleUseCase {
        return UpdateTaskTitleUseCase(taskItemRepository)
    }


    @Provides
    @Singleton
    fun provideUpdateTaskStatusUseCase(
        taskItemRepository: TaskItemRepository,
    ): UpdateTaskStatusUseCase {
        return UpdateTaskStatusUseCase(taskItemRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(
        taskItemRepository: TaskItemRepository,
    ): DeleteTaskUseCase {
        return DeleteTaskUseCase(taskItemRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskDescriptionUseCase(
        taskItemRepository: TaskItemRepository,
    ): UpdateTaskDescriptionUseCase {
        return UpdateTaskDescriptionUseCase(taskItemRepository)
    }


    @Provides
    @Singleton
    fun provideSaveCategoryFlashCard(
        taskItemRepository: CategoryFlashcardRepository,
    ): SaveCategoryFlashCard {
        return SaveCategoryFlashCard(taskItemRepository)
    }


    @Provides
    @Singleton
    fun provideGetAllCategoryFlashCard(
        taskItemRepository: CategoryFlashcardRepository,
    ): GetAllCategoryFlashCard {
        return GetAllCategoryFlashCard(taskItemRepository)
    }

    @Provides
    @Singleton
    fun provideSaveFlashCardByCategoryIDUseCase(
        flashCardRepository: FlashCardRepository,
    ): SaveFlashCardByCategoryIDUseCase {
        return SaveFlashCardByCategoryIDUseCase(flashCardRepository)
    }

    @Provides
    @Singleton
    fun provideGetFlashCardByCategoryIDUseCase(
        flashCardRepository: FlashCardRepository,
    ): GetFlashCardByCategoryIDUseCase {
        return GetFlashCardByCategoryIDUseCase(flashCardRepository)
    }

    @Provides
    @Singleton
    fun provideGenerateFlashCardUseCase(
        flashCardRepository: FlashCardRepository,
    ): GenerateFlashCardUseCase {
        return GenerateFlashCardUseCase(flashCardRepository)
    }


    @Provides
    @Singleton
    fun provideGenerateQuizUseCase(
        quizzRepository: QuizzRepository,
    ): GenerateQuizUseCase {
        return GenerateQuizUseCase(quizzRepository)
    }

    @Provides
    @Singleton
    fun provideAsKAIResponseUseCase(
        quizzRepository: AIChatRepository,
    ): AsKAIResponseUseCase {
        return AsKAIResponseUseCase(quizzRepository)
    }
}