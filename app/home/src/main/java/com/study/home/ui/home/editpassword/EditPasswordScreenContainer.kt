package com.study.home.ui.home.editpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.core.extension.toast

@Composable
fun EditPasswordScreenContainer(navController: NavHostController) {
    val viewModel: EditPasswordViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val applicationContext = navController.context

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                EditPasswordUiEvent.Close -> navController.popBackStack()
                is EditPasswordUiEvent.UpdateFailed -> applicationContext.toast("Cập nhật thất bại")
                EditPasswordUiEvent.UpdateSuccess -> applicationContext.toast("Cập nhật thành công")
            }
        }
    }

    EditPasswordScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}