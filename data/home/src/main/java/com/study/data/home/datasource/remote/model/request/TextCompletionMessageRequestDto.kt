package com.osprey.data.home.datasource.remote.model.request

import com.google.gson.annotations.SerializedName

data class TextCompletionMessageRequestDto(
    @SerializedName("role")
    val role: String,

    @SerializedName("content")
    val content: String
)