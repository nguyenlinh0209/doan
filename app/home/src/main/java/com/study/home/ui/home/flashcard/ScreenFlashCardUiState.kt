package com.study.home.ui.home.flashcard

import com.study.domain.home.model.local.CategoryFlashcard
import java.util.UUID

data class ScreenFlashCardUiState(
    val categories: List<CategoryFlashcard> = emptyList(),
    val selectedCategoryId: UUID? = null
)
