package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.response.FlashCardDetailResponse
import com.study.domain.home.repository.FlashCardRepository
import javax.inject.Inject

data class GenerateFlashCardParams(
    val count: Int,
    val input: String
)

class GenerateFlashCardUseCase @Inject constructor(
    private val repository: FlashCardRepository
) : BaseParamsUnsafeUseCase<GenerateFlashCardParams, List<FlashCardDetailResponse>>() {
    override suspend fun execute(
        params: GenerateFlashCardParams
    ): List<FlashCardDetailResponse> {
        return repository.generateFlashCard(
            count = params.count,
            input = params.input
        )
    }
}
