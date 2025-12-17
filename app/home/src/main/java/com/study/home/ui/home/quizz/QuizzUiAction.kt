package com.study.home.ui.home.quizz

import com.study.home.ui.home.flashcard.ScreenFlashCardUiAction

sealed class QuizzUiAction {
    object NavigateBack : QuizzUiAction()

    object AddNewQuizz : QuizzUiAction()

}