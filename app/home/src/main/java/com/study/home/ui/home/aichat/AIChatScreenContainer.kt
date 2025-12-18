package com.study.home.ui.home.aichat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController


@Composable
fun AIChatScreenContainer(navController: NavHostController) {
    val viewModel: AIChatViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    AIChatScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}
