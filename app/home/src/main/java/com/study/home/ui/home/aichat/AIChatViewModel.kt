package com.study.home.ui.home.aichat

import android.app.Application
import com.study.core.base.viewmodel.BaseUiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AIChatViewModel @Inject constructor(
    app: Application
) : BaseUiStateViewModel<AIChatUiState, AIChatUiEvent, AIChatUiAction>(app) {

    override fun initialState() = AIChatUiState()

    override fun handleAction(action: AIChatUiAction) {
        when (action) {
            is AIChatUiAction.SendMessage -> {
                sendMessage(action.message)
            }

            AIChatUiAction.Close -> TODO()
        }
    }

    private fun sendMessage(message: String) {
        if (message.isBlank()) return
        updateState {
            it.copy(
                messages =  message
            )
        }

        sendEvent(
            AIChatUiEvent.MessageSent(message)
        )
    }
}

