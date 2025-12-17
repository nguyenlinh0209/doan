package com.study.data.home.datasource.remote.datasource

interface  AIChatDataSource{
    suspend fun simpleAiChat(
        input: String,
        systemPrompt: String? = null
    ): String?
}