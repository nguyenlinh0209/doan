package com.study.home.ui.home.flashcard.managementflashcard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.core.extension.toast
import java.util.UUID

@Composable
fun FlashCardContainer(
    navController: NavHostController,
    categoryId: UUID
) {
    val viewModel: FlashCardViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.dispatch(FlashCardUiAction.LoadFlashCardsForCategory(categoryId))
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                FlashCardUiEvent.NavigateBack -> navController.popBackStack()
                is FlashCardUiEvent.SaveError ->context.toast("Lưu thất bại")
                FlashCardUiEvent.SaveSuccess -> context.toast("Lưu thành công")
            }
        }
    }

    FlashCardScreen(
        uiState = state,
        onAction = viewModel::dispatch
    )
}