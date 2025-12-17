package com.study.home.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreenContainer(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUiEvent.NavigateTo -> navController.navigate(event.screen.route)
                is HomeUiEvent.NavigateBack -> navController.popBackStack()
                is HomeUiEvent.NavigateToDeepLink -> navController.navigate(event.uri)
            }
        }
    }

    HomeScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}
