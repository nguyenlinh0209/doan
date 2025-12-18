package com.osprey.domain.remote.model.request

import com.osprey.domain.common.model.Sender

data class ChatMessage(
    var role: String = "",
    var content: List<ChatMessageContent> = arrayListOf()
) {

    val sender: Sender
        get() = Sender.entries.firstOrNull { it.value == role } ?: Sender.ASSISTANT

    val compareContent: String
        get() = content.joinToString(",") { it.content ?: "" }

}