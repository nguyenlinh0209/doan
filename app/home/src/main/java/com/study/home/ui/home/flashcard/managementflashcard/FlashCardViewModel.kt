package com.study.home.ui.home.flashcard.managementflashcard

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.home.usecase.GenerateFlashCardParams
import com.study.domain.home.usecase.GenerateFlashCardUseCase
import com.study.domain.home.usecase.GetFlashCardByCategoryIDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    app: Application,
    private val getFlashCardsUseCase: GetFlashCardByCategoryIDUseCase,
    private val generateFlashCardUseCase: GenerateFlashCardUseCase
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
        }
    }

    fun loadFlashCardsForCategory(categoryId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getFlashCardsUseCase(categoryId)
            }.onSuccess { flashCards ->
                updateState {
                    it.copy(flashCards = flashCards)
                }
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