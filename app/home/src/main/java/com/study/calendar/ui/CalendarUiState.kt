package com.study.calendar.ui

import com.study.domain.home.model.local.TaskItem
import java.util.UUID

data class CalendarUiState(
    val isLoading: Boolean = false,
    val userId: UUID? = null,
    val tasks: List<TaskItem> = emptyList()
)