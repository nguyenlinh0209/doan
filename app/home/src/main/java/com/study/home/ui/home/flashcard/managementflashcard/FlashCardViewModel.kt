package com.study.home.ui.home.flashcard.managementflashcard

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.usecase.GenerateFlashCardParams
import com.study.domain.home.usecase.GenerateFlashCardUseCase
import com.study.domain.home.usecase.GetFlashCardByCategoryIDUseCase
import com.study.domain.home.usecase.SaveFlashCardByCategoryIDUseCase
import com.study.domain.home.usecase.SaveFlashCardParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    app: Application,
    private val getFlashCardsUseCase: GetFlashCardByCategoryIDUseCase,
    private val generateFlashCardUseCase: GenerateFlashCardUseCase,
    private val saveFlashCardByCategoryIDUseCase: SaveFlashCardByCategoryIDUseCase,
) : BaseUiStateViewModel<FlashCardUiState, FlashCardUiEvent, FlashCardUiAction>(app) {

    override fun initialState(): FlashCardUiState = FlashCardUiState()

    override fun handleAction(action: FlashCardUiAction) {
        when (action) {
            FlashCardUiAction.OnBack -> navigateBack()
            is FlashCardUiAction.LoadFlashCardsForCategory -> loadFlashCardsForCategory(
                action.categoryId
            )

            is FlashCardUiAction.GenerateFlashCard ->
                generateFlashCard(
                    input = action.input,
                    count = action.count
                )

            is FlashCardUiAction.SaveGeneratedFlashCards -> saveGeneratedFlashCards(action.flashCards)
        }
    }

    fun loadFlashCardsForCategory(categoryId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getFlashCardsUseCase(categoryId)
            }.onSuccess { flashCards ->
                updateState {
                    it.copy(
                        flashCards = flashCards,
                        categoryFlashcardId = categoryId
                    )
                }
            }
        }
    }

    private fun saveGeneratedFlashCards(flashCards: List<FlashCard>) {
        viewModelScope.launch(Dispatchers.IO) {
            updateState { it.copy(isSaving = true, error = null) }

            val categoryId = currentState.categoryFlashcardId
            if (categoryId == null) {
                Log.e("FlashCardVM", "Category ID is null")
                updateState {
                    it.copy(
                        isSaving = false,
                        error = "Category ID is null"
                    )
                }
                sendEvent(FlashCardUiEvent.SaveError("Category ID is null"))
                return@launch
            }

            runCatching {
                saveFlashCardByCategoryIDUseCase(SaveFlashCardParams(categoryId, flashCards))
            }.onSuccess {
                Log.d("FlashCardVM", "Flashcards saved successfully: ${flashCards.size}")

                updateState {
                    it.copy(
                        isSaving = false,
                        generatedFlashCard = emptyList(),
                        flashCards = it.flashCards + flashCards
                    )
                }

                sendEvent(FlashCardUiEvent.SaveSuccess)
            }.onFailure { error ->
                Log.e("FlashCardVM", "Error saving flashcards", error)
                updateState {
                    it.copy(
                        isSaving = false,
                        error = error.message ?: "Unknown error"
                    )
                }
                sendEvent(FlashCardUiEvent.SaveError(error.message ?: "Unknown error"))
            }
        }
    }

    private fun generateFlashCard(input: String, count: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            updateState { it.copy(isGenerating = true) }

            val result = generateFlashCardUseCase(
                GenerateFlashCardParams(
                    input = input,
                    count = count
                )
            )
            Log.d(
                "FlashCardVM",
                "generatedFlashCard = $result"
            )

            updateState {
                it.copy(
                    isGenerating = false,
                    generatedFlashCard = result
                )
            }
        }
    }


    private fun navigateBack() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(FlashCardUiEvent.NavigateBack)
        }
    }
}