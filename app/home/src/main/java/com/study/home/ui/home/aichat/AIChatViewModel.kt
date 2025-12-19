package com.study.home.ui.home.aichat

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.osprey.domain.remote.model.request.ChatMessage
import com.osprey.domain.remote.model.request.ChatMessageContent
import com.osprey.domain.common.model.Sender
import com.study.common.extension.parseAIResponse
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.home.usecase.AsKAIResponseUseCase
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AIChatViewModel @Inject constructor(
    app: Application,
    private val askAIResponseUseCase: AsKAIResponseUseCase,
) : BaseUiStateViewModel<AIChatUiState, AIChatUiEvent, AIChatUiAction>(app) {

    override fun initialState() = AIChatUiState()

    override fun handleAction(action: AIChatUiAction) {
        when (action) {
            is AIChatUiAction.SendMessage -> {
                handleAskAI(action.message)
            }
            AIChatUiAction.Close -> {
                navigateBack()
            }
        }
    }

    private fun handleAskAI(message: String) {
        // 1. Add user message to list immediately
        val userMessage = ChatMessage(
            role = Sender.USER.value,
            content = listOf(
                ChatMessageContent(
                    type = "text",
                    text = message
                )
            )
        )

        updateState { state ->
            state.copy(
                messages = state.messages + userMessage,
                isLoading = true,
                error = null
            )
        }

        // 2. Call API to get AI response
        viewModelScope.launch(Dispatchers.Main) {
            askAIResponseUseCase(message).collect { result ->
                when (result) {
                    // Show loading state
                    is com.osprey.domain.base.Result.Loading -> {
                        updateState {
                            it.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }

                    // Handle error
                    is com.osprey.domain.base.Result.Error -> {
                        val errorMsg = result.exception?.message ?: "Đã có lỗi xảy ra"
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = errorMsg
                            )
                        }
                        sendEvent(AIChatUiEvent.Error(errorMsg))
                    }

                    is com.osprey.domain.base.Result.Success -> {
                        val rawResponse = result.data ?: ""
                        val cleanResponse = if (rawResponse.isNotBlank()) {
                            rawResponse.parseAIResponse()
                        } else {
                            ""
                        }

                        val aiMessage = ChatMessage(
                            role = Sender.ASSISTANT.value,
                            content = listOf(
                                ChatMessageContent(
                                    type = "text",
                                    text = cleanResponse
                                )
                            )
                        )

                        updateState { state ->
                            state.copy(
                                messages = state.messages + aiMessage,
                                isLoading = false,
                                error = null
                            )
                        }

                        if (cleanResponse.isNotBlank()) {
                            sendEvent(AIChatUiEvent.HandleGenerate(cleanResponse))
                        }
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(AIChatUiEvent.NavigateBack)
        }
    }
}