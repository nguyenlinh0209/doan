package com.study.home.ui.home.quizz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiEvent


@Composable
fun QuizzContainer(
    navController: NavHostController, ) {
    val viewModel: QuizzViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                FlashCardUiEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    QuizzScreen(
        uiState = state,
        onAction = viewModel::dispatch
    )
}