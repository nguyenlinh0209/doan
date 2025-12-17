package com.study.home.ui.home.flashcard

import com.study.home.navigation.Screen
import java.util.UUID

sealed class ScreenFlashCardUiEvent {
    data class NavigateTo(val screen: Screen) : ScreenFlashCardUiEvent()

    object NavigateBack : ScreenFlashCardUiEvent()

    data class CategoryCreated(val categoryName: String) : ScreenFlashCardUiEvent()

    data class NavigateToCategory(val categoryId: UUID) : ScreenFlashCardUiEvent()

}