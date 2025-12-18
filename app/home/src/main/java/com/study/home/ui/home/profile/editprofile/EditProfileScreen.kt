package com.study.home.ui.home.profile.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditProfileScreen(
    state: EditProfileUiState,
    onAction: (EditProfileUiAction) -> Unit
) {
    val user = state.user

    var fullName by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        user?.let {
            fullName = it.name.orEmpty()
            className = it.classStudy?.let { grade -> "Lớp $grade" } ?: ""
            bio = ""
            email = it.email.orEmpty()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Hủy",
                fontSize = 16.sp,
                color = Color(0xFF666666),
                modifier = Modifier
                    .width(50.dp)
                    .clickable { onAction(EditProfileUiAction.Back) }
            )

            Text(
                text = "Chỉnh sửa hồ sơ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Lưu",
                fontSize = 16.sp,
                color = Color(0xFF4A90E2),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .width(50.dp)
                    .clickable {
                        user?.let {
                            onAction(
                                EditProfileUiAction.SaveProfile(
                                    it.copy(
                                        name = fullName,
                                        classStudy = className
                                            .filter { c -> c.isDigit() }
                                            .toIntOrNull()
                                    )
                                )
                            )
                        }
                    }
            )
        }

        Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color(0x4D4A90E2), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = fullName.take(2).uppercase(),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF333333), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Text(
                text = "Thay đổi ảnh đại diện",
                fontSize = 14.sp,
                color = Color(0xFF4A90E2),
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            ProfileTextField(
                label = "HỌ VÀ TÊN",
                value = fullName,
                onValueChange = { fullName = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileTextField(
                label = "LỚP HỌC",
                value = className,
                onValueChange = { className = it },
                helper = "Thông tin này giúp gợi ý bài học phù hợp."
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileTextField(
                label = "GIỚI THIỆU BẢN THÂN",
                value = bio,
                onValueChange = { bio = it },
                singleLine = false,
                height = 100.dp,
                placeholder = "Nhập giới thiệu về bản thân..."
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileTextField(
                label = "EMAIL (KHÔNG THỂ ĐỔI)",
                value = email,
                enabled = false
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    singleLine: Boolean = true,
    height: Dp = Dp.Unspecified,
    placeholder: String? = null,
    helper: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF999999)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = singleLine,
            modifier = Modifier
                .fillMaxWidth()
                .then(if (height != Dp.Unspecified) Modifier.height(height) else Modifier)
                .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)),
            placeholder = placeholder?.let {
                { Text(it, color = Color(0xFFBBBBBB), fontSize = 14.sp) }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFAFAFA),
                unfocusedContainerColor = Color(0xFFFAFAFA),
                disabledContainerColor = Color(0xFFFAFAFA),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        helper?.let {
            Text(
                text = it,
                fontSize = 12.sp,
                color = Color(0xFFBBBBBB),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
