package com.study.camera.ui

sealed class CameraUiEvent {
    object OnBackPressed : CameraUiEvent()
    data class OnImageConfirmed(
        val imageUri: String,
        val extractedText: String = ""
    ) : CameraUiEvent()
}
