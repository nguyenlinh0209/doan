package com.study.home.ui.home.flashcard.managementflashcard

import com.study.domain.home.model.local.FlashCard
import java.util.UUID

sealed class FlashCardUiAction {
    object OnBack : FlashCardUiAction()
    data class LoadFlashCardsForCategory(val categoryId: UUID) : FlashCardUiAction()
    data class GenerateFlashCard(val input: String, val count: Int) : FlashCardUiAction()
    data class SaveGeneratedFlashCards(val flashCards: List<FlashCard>) : FlashCardUiAction()
}