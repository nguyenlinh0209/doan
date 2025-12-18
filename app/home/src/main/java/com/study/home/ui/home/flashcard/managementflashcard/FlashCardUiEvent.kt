package com.study.home.ui.home.flashcard.managementflashcard

sealed class FlashCardUiEvent {
    object NavigateBack : FlashCardUiEvent()
    object SaveSuccess : FlashCardUiEvent()
    data class SaveError(val message: String) : FlashCardUiEvent()
}