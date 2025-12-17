package com.study.calendar.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun CalendarScreenContainer(
    navController: NavHostController,
    onImageCaptured: (String) -> Unit = {}
) {
    val viewModel: CalendarViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {

            }
        }
    }

    CalendarScreen(
        uiState = state,
         onAction = viewModel::handleAction
    )
}