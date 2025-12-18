package com.study.home.ui.home.editpassword


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditPasswordScreen(
    state: EditPasswordUiState,
    onAction: (EditPasswordUiAction) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onAction(EditPasswordUiAction.Close) },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    "Đổi mật khẩu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            // Current Password
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    "MẬT KHẨU HIỆN TẠI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF888888),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                PasswordTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    placeholder = "Nhập mật khẩu cũ...",
                    showPassword = showCurrentPassword,
                    onShowPasswordChange = { showCurrentPassword = it }
                )
            }

            // New Password
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    "MẬT KHẨU MỚI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF888888),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                PasswordTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    placeholder = "Tối thiểu 6 ký tự",
                    showPassword = showNewPassword,
                    onShowPasswordChange = { showNewPassword = it }
                )
            }

            // Confirm Password
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    "NHẬP LẠI MẬT KHẨU MỚI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF888888),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                PasswordTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Xác nhận mật khẩu",
                    showPassword = showConfirmPassword,
                    onShowPasswordChange = { showConfirmPassword = it }
                )
            }

            // Password Requirements
            AnimatedVisibility(visible = newPassword.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFE3F2FD),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "LƯU Ý BẢO MẬT",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1976D2)
                    )

                    RequirementItem(
                        text = "Tối thiểu 6 ký tự",
                        isMet = newPassword.length >= 6
                    )

                    RequirementItem(
                        text = "Nên có chữ hoa và số",
                        isMet = newPassword.any { it.isUpperCase() } && newPassword.any { it.isDigit() }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Error Message
            if (!state.error.isNullOrBlank()) {
                Text(
                    text = state.error ?: "",
                    fontSize = 13.sp,
                    color = Color(0xFFE53935),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(
                            color = Color(0xFFFFEBEE),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                )
            }

            // Update Button
            Button(
                onClick = {
                    onAction(
                        EditPasswordUiAction.UpdatePassword(
                            currentPassword = currentPassword,
                            newPassword = newPassword,
                            confirmPassword = confirmPassword
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isLoading) Color(0xFFCCCCCC) else Color(0xFF1ABC9C),
                    disabledContainerColor = Color(0xFFCCCCCC)
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = !state.isLoading && currentPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Cập nhật mật khẩu",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    showPassword: Boolean,
    onShowPasswordChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                color = Color(0xFFBBBBBB),
                fontSize = 14.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            IconButton(onClick = { onShowPasswordChange(!showPassword) }) {
                Icon(
                    imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle password visibility",
                    tint = Color(0xFF888888),
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1ABC9C),
            unfocusedBorderColor = Color(0xFFEEEEEE),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 14.sp,
            color = Color.Black
        )
    )
}

@Composable
private fun RequirementItem(text: String, isMet: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = if (isMet) Color(0xFF4CAF50) else Color(0xFF1976D2),
                    shape = RoundedCornerShape(3.dp)
                )
        )

        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF1976D2)
        )
    }
}