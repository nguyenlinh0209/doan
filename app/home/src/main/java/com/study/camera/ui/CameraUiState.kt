package com.study.camera.ui

data class CameraUiState(
    val capturedImageUri: String = "",
    val extractedText: String = "",
    val isProcessing: Boolean = false,
    val error: String? = null
)
