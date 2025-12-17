package com.study.home.ui.home.quizz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardScreen
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiAction
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiEvent
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardViewModel
import java.util.UUID


@Composable
fun QuizzContainer(
    navController: NavHostController,
    categoryId: UUID
) {
    val viewModel: FlashCardViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(FlashCardUiAction.LoadFlashCardsForCategory(categoryId))
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                FlashCardUiEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    FlashCardScreen(
        uiState = state,
        onAction = viewModel::dispatch
    )
}