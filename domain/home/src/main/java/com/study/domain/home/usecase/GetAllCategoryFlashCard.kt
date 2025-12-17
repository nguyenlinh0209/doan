package com.study.domain.home.usecase

import com.osprey.domain.base.BaseNoParamsUnsafeUseCase
import com.study.domain.home.model.local.CategoryFlashcard
import com.study.domain.home.repository.CategoryFlashcardRepository
import javax.inject.Inject

class GetAllCategoryFlashCard @Inject constructor(
    private val repository: CategoryFlashcardRepository
) : BaseNoParamsUnsafeUseCase<List<CategoryFlashcard>>() {

    override suspend fun execute(): List<CategoryFlashcard> {
        return repository.getAllCategories().getOrThrow()
    }
}
