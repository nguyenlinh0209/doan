package com.study.home.ui.home.profile.editprofile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.core.extension.toast


@Composable
fun EditProfileContainer(navController: NavHostController) {
    val viewModel: EditProfileViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val applicationContext = navController.context

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                EditProfileUiEvent.ProfileSaved -> applicationContext.toast("Cập nhật thành công")
                EditProfileUiEvent.Back -> navController.popBackStack()
            }
        }
    }
    EditProfileScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}