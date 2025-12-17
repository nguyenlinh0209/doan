package com.study.home.ui.home.flashcard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.core.extension.toast
import com.study.home.navigation.Screen

@Composable
fun ScreenFlashCardContainer(navController: NavHostController) {
    val viewModel: ScreenFlashCardViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ScreenFlashCardUiEvent.NavigateTo -> navController.navigate(event.screen.route)
                is ScreenFlashCardUiEvent.NavigateToCategory -> {
                    navController.navigate(
                        Screen.FlashCard.createRoute(event.categoryId)
                    )
                }
                ScreenFlashCardUiEvent.NavigateBack -> navController.popBackStack()
                is ScreenFlashCardUiEvent.CategoryCreated -> context.toast("Thêm thành công")
            }
        }
    }

    ScreenFlashCard(
        uiState  = state,
        onAction = viewModel::dispatch
    )
}