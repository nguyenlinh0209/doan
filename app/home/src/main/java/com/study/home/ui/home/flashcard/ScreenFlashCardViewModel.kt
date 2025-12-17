package com.study.home.ui.home.flashcard

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.home.model.local.CategoryFlashcard
import com.study.domain.home.usecase.GetAllCategoryFlashCard
import com.study.domain.home.usecase.SaveCategoryFlashCard
import com.study.home.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class ScreenFlashCardViewModel @Inject constructor(
    app: Application,
    private val saveCategory: SaveCategoryFlashCard,
    private val getAllCategory: GetAllCategoryFlashCard
) : BaseUiStateViewModel<ScreenFlashCardUiState, ScreenFlashCardUiEvent, ScreenFlashCardUiAction>(
    app
) {

    override fun initialState() = ScreenFlashCardUiState()

    override fun handleAction(action: ScreenFlashCardUiAction) {
        when (action) {
            ScreenFlashCardUiAction.AddNewCard -> navigateAddFlashCard()
            ScreenFlashCardUiAction.OnBack -> navigateBack()
            is ScreenFlashCardUiAction.CreateCategory -> createCategory(action.categoryName)
            is ScreenFlashCardUiAction.SelectCategory -> navigateToCategory(action.categoryId)
        }
    }

    init {
        loadCategories()
    }


    private fun loadCategories() {
        viewModelScope.launch {
            runCatching {
                getAllCategory()
            }.onSuccess { categories ->
                if (categories.isEmpty()) {
                    createDefaultCategories()
                } else {
                    updateState { it.copy(categories = categories) }
                }
            }
        }
    }

    private suspend fun createDefaultCategories() {
        val defaults = listOf("Work", "All")

        val categories = defaults.map { name ->
            CategoryFlashcard(name = name)
        }

        categories.forEach { category ->
            saveCategory(category)
        }
        updateState {
            it.copy(
                categories = defaults.map { name ->
                    CategoryFlashcard(name = name)
                }
            )
        }
    }

    private fun navigateAddFlashCard() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(ScreenFlashCardUiEvent.NavigateTo(Screen.AddFlashCard))
        }
    }

    private fun navigateBack() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(ScreenFlashCardUiEvent.NavigateBack)
        }
    }

    private fun createCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val category = CategoryFlashcard(name = categoryName)
            saveCategory(category)
            val categories = getAllCategory()
            updateState {
                it.copy(categories = categories)
            }
            withContext(Dispatchers.Main){
                sendEvent(ScreenFlashCardUiEvent.CategoryCreated(categoryName))
            }
        }
    }

    private fun navigateToCategory(categoryId: UUID) {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(ScreenFlashCardUiEvent.NavigateToCategory(categoryId))
        }
    }

}