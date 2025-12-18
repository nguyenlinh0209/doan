package com.study.home.ui.home.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.common.navigation.AuthNavigator
import com.study.home.navigation.Screen

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
                ProfileScreenUiEvent.NavigateEdit -> {
                    navController.navigate(Screen.EDITPROFILE.route)
                }
                ProfileScreenUiEvent.NavigateSignOut ->
                    authNavigator.showSignIn(
                        context = context,
                        isShowSignUp = false
                    )

                ProfileScreenUiEvent.NavigateChangePassword ->    navController.navigate(Screen.EditPassword.route)
            }
        }
    }

    ProfileScreen(
        state = state,
        onAction = viewModel::dispatch
    )
}
