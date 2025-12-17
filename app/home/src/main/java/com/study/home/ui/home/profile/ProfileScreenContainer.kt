package com.study.home.ui.home.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.common.navigation.AuthNavigator

@Composable
fun ProfileScreenContainer(
    navController: NavHostController,
    authNavigator: AuthNavigator
) {
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = navController.context

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                ProfileScreenUiEvent.NavigateSignOut -> {
                    authNavigator.showSignIn(
                        context = context,
                        isShowSignUp = false
                    )
                }
            }
        }
    }

    ProfileScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}
