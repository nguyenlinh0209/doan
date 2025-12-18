package com.study.domain.user.model

import java.util.Date
import java.util.UUID


data class User(
    var id: UUID = UUID.randomUUID(),
    var email: String? = "",
    var password: String = "",
    var name: String? = "",
    var classStudy: Int? = null,
    var isActive: Boolean = false,
    var createdAt: Date = Date(),
    var updatedAt: Date = Date(),
    var deletedAt: Date? = null,
)
