package com.study.camera.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun CameraScreenContainer(
    navController: NavHostController,
    onImageCaptured: (String) -> Unit = {}
) {
    val viewModel: CameraViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.dispatch(CameraUiAction.OnImageSelected(it.toString()))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                CameraUiEvent.OnBackPressed -> {
                    navController.popBackStack()
                }

                is CameraUiEvent.OnImageConfirmed -> {
                    onImageCaptured(event.imageUri)
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "captured_image_uri",
                        event.imageUri
                    )

                    navController.popBackStack()
                }
            }
        }
    }

    CameraScreen(
        uiState = state,
        onAction = { action ->
            when (action) {
                is CameraUiAction.OnImageSelected -> {
                    galleryLauncher.launch("image/*")
                }
                else -> viewModel.dispatch(action)
            }
        }
    )
}