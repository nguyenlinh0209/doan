package com.study.camera.ui

sealed class CameraUiAction {
    object OnBackPressed : CameraUiAction()
    data class OnImageCaptured(val imageUri: String) : CameraUiAction()
    data class OnImageSelected(val imageUri: String) : CameraUiAction()
    object OnCaptureClicked : CameraUiAction()
    object OnConfirmClicked : CameraUiAction()
    object OnRetakeClicked : CameraUiAction()
}