package com.study.home.ui.home.flashcard.managementflashcard

import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.model.local.response.FlashCardDetailResponse
import java.util.UUID

data class FlashCardUiState (
    val flashCards: List<FlashCard> = emptyList(),
    val isGenerating: Boolean = false,
    val generatedFlashCard: List<FlashCardDetailResponse> = emptyList(),
    val categoryFlashcardId : UUID? = null,
    val isSaving: Boolean = false,
)