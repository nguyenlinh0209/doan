package com.osprey.domain.home.model.local

import java.time.LocalDate

data class CalendarDayData(
    val localDate: LocalDate,
    val day: String,
    val isCurrentMonth: Boolean = true,
    val isSelected: Boolean = false,
    val hasEvent: Boolean = false
)
