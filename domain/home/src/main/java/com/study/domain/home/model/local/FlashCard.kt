package com.study.domain.home.model.local

import java.util.Date
import java.util.UUID

data class FlashCard(
    val id: UUID = UUID.randomUUID(),
    val categoryId : UUID,
    val front: String,
    val back: String,
    val created_at: Date
)
