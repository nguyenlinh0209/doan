package com.study.camera.ui

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    uiState: CameraUiState,
    onAction: (CameraUiAction) -> Unit,
    onOpenCamera: (Uri) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var tempImageUri: Uri? by remember { mutableStateOf(null) }
    var showCameraPreview by remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempImageUri != null) {
            onAction(CameraUiAction.OnImageCaptured(tempImageUri.toString()))
            showCameraPreview = false
        }
    }

    // Launcher để chọn ảnh từ gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            onAction(CameraUiAction.OnImageSelected(it.toString()))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a))
    ) {
        // Hiển thị ảnh đã chụp (nếu có)
        if (uiState.capturedImageUri.isNotEmpty()) {
            AsyncImage(
                model = uiState.capturedImageUri,
                contentDescription = "Captured Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Overlay semi-transparent
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        } else if (showCameraPreview) {
            // Hiển thị live camera preview
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                        val executor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ctx.mainExecutor
                        } else {
                            ContextCompat.getMainExecutor(ctx)
                        }

                        cameraProviderFuture.addListener({
                            try {
                                val cameraProvider = cameraProviderFuture.get()
                                val preview = Preview.Builder().build().also {
                                    it.setSurfaceProvider(surfaceProvider)
                                }

                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }, executor)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Header với nút back
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onAction(CameraUiAction.OnBackPressed)
                showCameraPreview = false
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Hiển thị lỗi (nếu có)
        if (uiState.error != null) {
            Text(
                text = uiState.error,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp)
            )
        }

        // Hiển thị loading
        if (uiState.isProcessing) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }

        // Bottom controls
        if (uiState.capturedImageUri.isEmpty() && !showCameraPreview) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp, start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Nút mở gallery
                    IconButton(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Photo,
                            contentDescription = "Gallery",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Nút chụp ảnh
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(Color.Transparent)
                            .clickable {
                                if (cameraPermissionState.status.isGranted) {
                                    try {
                                        val tempFile = createTempImageFile(context)
                                        tempImageUri = androidx.core.content.FileProvider.getUriForFile(
                                            context,
                                            "${context.packageName}.fileprovider",
                                            tempFile
                                        )
                                        showCameraPreview = true
                                        cameraLauncher.launch(tempImageUri!!)
                                    } catch (e: Exception) {
                                        onAction(CameraUiAction.OnImageCaptured(""))
                                    }
                                } else {
                                    cameraPermissionState.launchPermissionRequest()
                                }
                            }
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Take Photo",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(56.dp))
                }
            }
        } else if (uiState.capturedImageUri.isNotEmpty()) {
            // Nút xác nhận/retake khi đã chụp ảnh
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onAction(CameraUiAction.OnRetakeClicked) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Retake")
                }

                Button(
                    onClick = { onAction(CameraUiAction.OnConfirmClicked) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

private fun createTempImageFile(context: android.content.Context): java.io.File {
    val storageDir = context.cacheDir
    return java.io.File.createTempFile(
        "IMG_${System.currentTimeMillis()}",
        ".jpg",
        storageDir
    )
}