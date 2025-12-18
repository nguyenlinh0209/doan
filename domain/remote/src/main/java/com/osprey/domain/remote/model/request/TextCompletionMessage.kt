package com.starnest.domain.remote.model.request

import com.osprey.domain.common.model.Sender

data class TextCompletionMessage(
    var role: String = "",
    var content: String = "",
    var name: String? = null,
) {
    val sender: Sender
        get() = Sender.entries.firstOrNull { it.value == role } ?: Sender.ASSISTANT

}