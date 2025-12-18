package com.study.domain.home.repository

interface AIChatRepository {
    suspend fun askAI(input: String): String?
}