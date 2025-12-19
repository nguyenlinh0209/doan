package com.study.home.ui.home.aichat

import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiEvent

sealed class AIChatUiEvent {
    data class MessageSent(val message: String) : AIChatUiEvent()
    data class HandleGenerate(val message: String) : AIChatUiEvent()
    data class Error(val error: String) : AIChatUiEvent()

    object NavigateBack : AIChatUiEvent()

}
