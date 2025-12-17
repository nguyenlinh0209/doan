package com.study.domain.home.repository

import com.study.domain.home.model.local.CategoryFlashcard

interface CategoryFlashcardRepository {

    suspend fun addCategory(category: CategoryFlashcard): CategoryFlashcard

    suspend fun getAllCategories(): Result<List<CategoryFlashcard>>

}

