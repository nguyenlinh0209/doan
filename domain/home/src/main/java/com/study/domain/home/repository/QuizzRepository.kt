package com.study.domain.home.repository

import com.study.domain.home.model.local.Quizz
import com.study.domain.home.model.local.response.Question

interface QuizzRepository {
    suspend fun saveFlashCard(flashCard: Quizz): Quizz
    suspend fun generateQuiz(
        count:Int,
        input: String
    ): List<Question>
}