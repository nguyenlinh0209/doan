package com.study.intro.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.intro.ui.intro.IntroUiEvent

@Composable
fun SlashScreenContainer(navController: NavHostController) {
    val viewModel: SlashViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                IntroUiEvent.NavigateToMain -> TODO()
            }
        }
    }

    SlashScreen(
        uiState = state,
        onAction = viewModel::dispatch
    )
}
