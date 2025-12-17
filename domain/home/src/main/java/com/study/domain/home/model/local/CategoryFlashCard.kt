package com.study.domain.home.model.local

import java.util.UUID

data class CategoryFlashcard(
    val id :UUID = UUID.randomUUID(),
    val name:String
)