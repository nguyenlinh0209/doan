package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.repository.FlashCardRepository
import java.util.UUID
import javax.inject.Inject

data class SaveFlashCardParams(
    val categoryId: UUID,
    val flashCards: List<FlashCard>
)

class SaveFlashCardByCategoryIDUseCase @Inject constructor(
    private val flashCardRepository: FlashCardRepository
) : BaseParamsUnsafeUseCase<SaveFlashCardParams, Unit>() {
    override suspend fun execute(params: SaveFlashCardParams): Unit {
        params.flashCards.forEach { flashCard ->
            flashCardRepository.saveFlashCard(
                flashCard.copy(categoryId = params.categoryId)
            )
        }
    }
}
