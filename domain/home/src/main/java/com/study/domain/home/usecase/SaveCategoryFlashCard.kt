package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.CategoryFlashcard
import com.study.domain.home.repository.CategoryFlashcardRepository
import javax.inject.Inject

class SaveCategoryFlashCard @Inject constructor(
    private val repository: CategoryFlashcardRepository
) : BaseParamsUnsafeUseCase<CategoryFlashcard, CategoryFlashcard?>() {
    override suspend fun execute(params: CategoryFlashcard): CategoryFlashcard {
        return repository.addCategory(params)
    }
}
