package com.study.home.ui.home.aichat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController


@Composable
fun AIChatScreenContainer(navController: NavHostController) {
    val viewModel: AIChatViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AIChatUiEvent.MessageSent -> TODO()
            }
        }
    }

    AIChatScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}
