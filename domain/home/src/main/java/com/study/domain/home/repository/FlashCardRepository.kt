package com.study.domain.home.repository

import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.model.local.response.FlashCardDetailResponse
import java.util.UUID

interface FlashCardRepository {
    suspend fun saveFlashCard(flashCard: FlashCard): FlashCard

    suspend fun getAllFlashCardByCategoryId(categoryId: UUID): Result<List<FlashCard>>

    suspend fun generateFlashCard(
        count:Int,
        input: String
    ): List<FlashCardDetailResponse>
}