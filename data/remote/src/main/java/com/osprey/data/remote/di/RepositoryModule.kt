package com.osprey.data.remote.di

import android.app.Application
import com.osprey.data.remote.model.datasource.AIDataSource
import com.osprey.data.remote.model.mapper.TextCompletionRequestMapper
import com.wodox.data.remote.model.mapper.TextCompletionResponseMapper
import com.osprey.data.remote.repository.AIChatRepositoryImpl
import com.starnest.domain.remote.repository.AIChatRepository
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
    fun provideAIChatRepository(
        app: Application,
        aiChatDataSource: AIDataSource,
        textCompletionResponseMapper: TextCompletionResponseMapper,
        textCompletionRequestMapper: TextCompletionRequestMapper,
    ): AIChatRepository {
        return AIChatRepositoryImpl(
            app,
            aiChatDataSource = aiChatDataSource,
            textCompletionResponseMapper = textCompletionResponseMapper,
            textCompletionRequestMapper = textCompletionRequestMapper,
        )
    }
}