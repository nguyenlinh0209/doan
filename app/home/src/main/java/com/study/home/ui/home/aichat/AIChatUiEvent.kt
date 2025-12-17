package com.study.home.ui.home.aichat

sealed class AIChatUiEvent {
    data class MessageSent(val message: String) : AIChatUiEvent()
}
