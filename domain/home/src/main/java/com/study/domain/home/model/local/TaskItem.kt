package com.study.domain.home.model.local

import java.time.LocalDateTime
import java.util.UUID

data class TaskItem(
    val id: UUID = UUID.randomUUID(),
    val dateTime: LocalDateTime,
    val title: String,
    val description: String,
    val color: Long,
    val isCompleted: Boolean = false,
    val userId: UUID? = null
)
