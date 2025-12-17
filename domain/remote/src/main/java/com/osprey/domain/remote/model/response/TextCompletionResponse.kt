package com.starnest.domain.remote.model.response

data class TextCompletionResponse(
    val data: TextCompletionData? = null,
    val error: String? = null,
) {
}