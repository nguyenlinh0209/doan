package com.study.home.ui.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle

@Composable
fun ProfileScreen(
    state: ProfileScreenUiState,
    onAction: (ProfileScreenUiAction) -> Unit
) {
    val user = state.user
    var isDarkMode by remember { mutableStateOf(false) }

    val backgroundColor = if (isDarkMode) Color(0xFF1A1A1A) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black
    val cardColor = if (isDarkMode) Color(0xFF2D2D2D) else Color.White
    val borderColor = if (isDarkMode) Color(0xFF404040) else Color(0xFFE0E0E0)
    val secondaryTextColor = if (isDarkMode) Color(0xFFB0B0B0) else Color.Gray

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            onAction(ProfileScreenUiAction.OnResume)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF1BC77D), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user?.name?.take(2)?.uppercase() ?: "",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = user?.name.orEmpty(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFFFD4D4),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Lớp ${user?.classStudy ?: "-"}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFE74C3C)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = user?.email.orEmpty(),
                        fontSize = 12.sp,
                        color = secondaryTextColor
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onAction(ProfileScreenUiAction.EditProfile) },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A90E2),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Chỉnh sửa hồ sơ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFFFE0B2), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🔥", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            "CHUỖI NGÀY HỌC",
                            fontSize = 11.sp,
                            color = secondaryTextColor,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "12 ngày liên tục",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== SETTINGS =====
            Text(
                "CÀI ĐẶT CHUNG",
                fontSize = 12.sp,
                color = secondaryTextColor,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🌙", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Giao diện tối",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    )
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { isDarkMode = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ===== CHANGE PASSWORD =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onAction(ProfileScreenUiAction.ChangePassword) },
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFFD4E5FF), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🔒", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Đổi mật khẩu",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = textColor
                        )
                    }
                    Text(">", fontSize = 20.sp, color = secondaryTextColor)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onAction(ProfileScreenUiAction.SignOut) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                )
            ) {
                Text(
                    "ĐĂNG XUẤT",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}