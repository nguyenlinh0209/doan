package com.study.home.ui.home.aichat

import com.osprey.domain.remote.model.request.ChatMessage

data class AIChatUiState(
    val isLoading: Boolean = false,
    val aiResponse: String? = null,
    val error: String? = null,
    val messages: List<ChatMessage> = emptyList()
)
