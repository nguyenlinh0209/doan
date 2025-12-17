package com.study.home.ui.home.quizz

import com.study.domain.home.model.local.Quizz
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiAction


sealed class QuizzUiAction {
    object NavigateBack : QuizzUiAction()
    object AddNewQuizz : QuizzUiAction()
    data class SelectQuizz(val quiz: Quizz) : QuizzUiAction()

    data object ClearQuestions : QuizzUiAction()

    data class CreateQuizz(
        val title: String,
        val description: String,
        val questionCount: Int
    ) : QuizzUiAction()
}