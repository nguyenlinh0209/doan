package com.osprey.domain.home.model.local

data class CalendarDay(
    val day: String,
    val isCurrentMonth: Boolean = true,
    val isSelected: Boolean = false,
    val hasEvent: Boolean = false
)