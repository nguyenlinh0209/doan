package com.osprey.domain.remote.model.request

import com.starnest.domain.remote.model.request.ChatMessageContentImage


data class ChatMessageContent(
    val type: String,
    var text: String? = null,
    val imageUrl: ChatMessageContentImage? = null,
)  {
    val content: String?
        get() = if (type == "text") {
            text
        } else {
            imageUrl?.url
        }

    val isImageUrl
        get() = type == "image_url"
}