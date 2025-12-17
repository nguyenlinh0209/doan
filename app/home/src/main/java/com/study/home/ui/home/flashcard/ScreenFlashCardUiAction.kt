package com.study.home.ui.home.flashcard

import java.util.UUID

sealed class ScreenFlashCardUiAction {
    object AddNewCard : ScreenFlashCardUiAction()
    object OnBack : ScreenFlashCardUiAction()
    data class CreateCategory(val categoryName: String) : ScreenFlashCardUiAction()

    data class SelectCategory(val categoryId: UUID) : ScreenFlashCardUiAction()

}