package com.study.home.ui.home.aichat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.core.extension.toast


@Composable
fun AIChatScreenContainer(navController: NavHostController) {
    val viewModel: AIChatViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AIChatUiEvent.Error -> context.toast("Gửi thất bại")
                is AIChatUiEvent.HandleGenerate -> context.toast("Tạo thành công")
                is AIChatUiEvent.MessageSent -> context.toast("Gửi thành công")
                AIChatUiEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    AIChatScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}
