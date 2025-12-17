package com.study.home.ui.home.quizz

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.home.usecase.GenerateQuizParams
import com.study.domain.home.usecase.GenerateQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizzViewModel @Inject constructor(
    app: Application,
    private val generateQuizUseCase: GenerateQuizUseCase,
) : BaseUiStateViewModel<QuizzUiState, QuizzUiEvent, QuizzUiAction>(app) {
    override fun initialState(): QuizzUiState = QuizzUiState()

    override fun handleAction(action: QuizzUiAction) {
        super.handleAction(action)
        when (action) {
            QuizzUiAction.AddNewQuizz -> TODO()
            is QuizzUiAction.CreateQuizz -> generateQuiz(
                action.title,
                action.description,
                action.questionCount
            )

            QuizzUiAction.NavigateBack -> TODO()
            is QuizzUiAction.SelectQuizz -> TODO()
            QuizzUiAction.ClearQuestions -> {
                updateState {
                    it.copy(archivedQuestion = emptyList())
                }
            }
        }
    }

    private fun generateQuiz(title: String, description: String, questionCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updateState { it.copy(isGenerating = true) }
            val result = generateQuizUseCase(
                GenerateQuizParams(
                    input = title + description,
                    count = questionCount
                )
            )
            Log.d(
                "Quiz",
                "generatedQuizz = $result"
            )

            updateState {
                it.copy(
                    isGenerating = false,
                    archivedQuestion = result
                )
            }
        }
    }
}
