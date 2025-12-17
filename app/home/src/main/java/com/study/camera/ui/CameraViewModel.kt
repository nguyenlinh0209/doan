package com.study.camera.ui

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.study.core.base.viewmodel.BaseUiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import com.study.core.extension.cacheAppDir
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class CameraViewModel @Inject constructor(
    app: Application
) : BaseUiStateViewModel<CameraUiState, CameraUiEvent, CameraUiAction>(app) {

    private var currentImageUri: Uri? = null
    private var tempImageFile: File? = null
    private val recognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    override fun initialState() = CameraUiState()

    override fun handleAction(action: CameraUiAction) {
        when (action) {
            is CameraUiAction.OnBackPressed -> handleBackPressed()
            is CameraUiAction.OnImageCaptured -> handleImageCaptured(action.imageUri)
            is CameraUiAction.OnImageSelected -> handleImageSelected(action.imageUri)
            CameraUiAction.OnCaptureClicked -> handleCaptureClicked()
            CameraUiAction.OnConfirmClicked -> handleConfirmClicked()
            CameraUiAction.OnRetakeClicked -> handleRetakeClicked()
        }
    }

    private fun handleCaptureClicked() {
        viewModelScope.launch {
            try {
                tempImageFile = createImageFile()
                tempImageFile?.let { file ->
                    val ctx = context?.get() ?: throw IllegalStateException("Context is null")
                    currentImageUri = FileProvider.getUriForFile(
                        ctx,
                        "${ctx.packageName}.fileprovider",
                        file
                    )
                }
            } catch (e: Exception) {
                updateState { it.copy(error = e.message) }
            }
        }
    }

    private fun handleImageCaptured(imageUri: String) {
        viewModelScope.launch {
            try {
                if (imageUri.isEmpty()) {
                    updateState { it.copy(error = "Failed to capture image") }
                    return@launch
                }

                updateState {
                    it.copy(
                        capturedImageUri = imageUri,
                        error = null
                    )
                }
            } catch (e: Exception) {
                updateState { it.copy(error = e.message) }
            }
        }
    }

    private fun handleImageSelected(imageUri: String) {
        viewModelScope.launch {
            try {
                if (imageUri.isEmpty()) {
                    updateState { it.copy(error = "Failed to select image") }
                    return@launch
                }

                updateState {
                    it.copy(
                        capturedImageUri = imageUri,
                        error = null
                    )
                }
            } catch (e: Exception) {
                updateState { it.copy(error = e.message) }
            }
        }
    }

    private fun handleRetakeClicked() {
        viewModelScope.launch {
            try {
                tempImageFile?.delete()

                updateState {
                    it.copy(
                        capturedImageUri = "",
                        extractedText = "",
                        error = null
                    )
                }
            } catch (e: Exception) {
                updateState { it.copy(error = e.message) }
            }
        }
    }

    private fun handleConfirmClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUri = uiState.value.capturedImageUri

                if (imageUri.isEmpty()) {
                    updateState { it.copy(error = "No image to process") }
                    return@launch
                }

                updateState { it.copy(isProcessing = true) }

                val extractedText = scanTextFromImage(imageUri)

                if (extractedText.isEmpty()) {
                    updateState {
                        it.copy(
                            error = "No text found in image",
                            isProcessing = false
                        )
                    }
                } else {
                    updateState {
                        it.copy(
                            extractedText = extractedText,
                            isProcessing = false
                        )
                    }
                    sendEvent(CameraUiEvent.OnImageConfirmed(imageUri, extractedText))
                }

            } catch (e: Exception) {
                updateState {
                    it.copy(
                        error = e.message ?: "Error processing image",
                        isProcessing = false
                    )
                }
            }
        }
    }

    private suspend fun scanTextFromImage(imageUri: String): String {
        return try {
            val bitmap = loadBitmapFromUri(imageUri)
                ?: throw Exception("Cannot load image")

            val image = InputImage.fromBitmap(bitmap, 0)
            val task = recognizer.process(image)

            val result = task.result
            val extractedText = buildString {
                result.textBlocks.forEach { block ->
                    block.lines.forEach { line ->
                        appendLine(line.text)
                    }
                }
            }

            extractedText.trim()
        } catch (e: Exception) {
            throw Exception("Text recognition failed: ${e.message}")
        }
    }

    private suspend fun loadBitmapFromUri(imageUri: String): Bitmap? {
        return try {
            val ctx = context?.get() ?: return null
            val uri = imageUri.toUri()

            Log.d("BitmapLoad", "URI: $uri")
            Log.d("BitmapLoad", "File exists: ${File(uri.path ?: "").exists()}")

            ctx.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            } ?: run {
                Log.e("BitmapLoad", "ContentResolver returned null")
                null
            }
        } catch (e: Exception) {
            Log.e("BitmapLoad", "Error loading bitmap", e)
            null
        }
    }

    private fun handleBackPressed() {
        viewModelScope.launch {
            try {
                tempImageFile?.delete()
                sendEvent(CameraUiEvent.OnBackPressed)
            } catch (e: Exception) {
                updateState { it.copy(error = e.message) }
            }
        }
    }

    private fun createImageFile(): File {
        val storageDir: File = applicationContext().cacheAppDir
        return File.createTempFile(
            "IMG_${System.currentTimeMillis()}",
            ".jpg",
            storageDir
        )
    }

    override fun onCleared() {
        super.onCleared()
        tempImageFile?.delete()
    }
}