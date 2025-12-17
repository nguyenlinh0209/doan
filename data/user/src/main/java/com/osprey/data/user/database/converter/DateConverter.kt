package com.osprey.data.user.database.converter

import java.util.Date

class DateConverter {
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }
    fun dateToTimestamp(date: Date?): Long? = date?.time
}