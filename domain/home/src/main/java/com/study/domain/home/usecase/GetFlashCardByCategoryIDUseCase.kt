package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.repository.FlashCardRepository
import java.util.UUID
import javax.inject.Inject

class GetFlashCardByCategoryIDUseCase @Inject constructor(
    private val repository: FlashCardRepository
) : BaseParamsUnsafeUseCase<UUID, List<FlashCard>>() {
    override suspend fun execute(params: UUID): List<FlashCard> {
        return repository
            .getAllFlashCardByCategoryId(params)
            .getOrThrow()
    }
}
