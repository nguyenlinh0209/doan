package com.study.data.home.repository

import android.util.Log
import com.study.common.util.PromptUtils
import com.study.data.home.datasource.remote.datasource.AIChatDataSource
import com.study.domain.home.repository.AIChatRepository
import javax.inject.Inject


class AIChatRepositoryImpl @Inject constructor(
    private val aiChatDataSource: AIChatDataSource,
) : AIChatRepository {
    override suspend fun askAI(input: String): String? {
        return try {
            val systemPrompt = PromptUtils.homeworkPrompt(input)
            val response = aiChatDataSource.simpleAiChat(
                input = input,
                systemPrompt = systemPrompt
            )
            response
        } catch (e: Exception) {
            Log.e("TaskRepository", "Error asking AI: ${e.message}", e)
            null
        }
    }
}