package com.study.home.ui.home.aichat

sealed interface AIChatUiAction {
    data class SendMessage(val message: String) : AIChatUiAction
    object Close : AIChatUiAction
}
