package com.study.home.ui.home.quizz

import com.study.domain.home.model.local.Quizz
import com.study.domain.home.model.local.response.Question

data class QuizzUiState(
    val quizzes: List<Quizz> = emptyList(),
    val archivedQuizzes: List<Quizz> = emptyList(),
    val archivedQuestion: List<Question> = emptyList(),
    val isGenerating: Boolean = false,
    )
