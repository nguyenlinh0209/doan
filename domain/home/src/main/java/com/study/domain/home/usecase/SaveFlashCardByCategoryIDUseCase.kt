package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.repository.FlashCardRepository
import javax.inject.Inject

class SaveFlashCardByCategoryIDUseCase @Inject constructor(
    private val userRepository: FlashCardRepository
) : BaseParamsUnsafeUseCase<FlashCard, FlashCard?>() {
    override suspend fun execute(params: FlashCard): FlashCard? {
        return userRepository.saveFlashCard(params)
    }
}