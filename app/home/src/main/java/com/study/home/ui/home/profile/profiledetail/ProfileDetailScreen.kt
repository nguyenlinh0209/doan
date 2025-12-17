package com.study.home.ui.home.profile.profiledetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NoteTakingScreen() {
    var selectedTab by remember { mutableStateOf("my_notes") }
    var showNoteMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1BC77D), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Tab Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Học tập của tôi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedTab == "my_notes") Color.Black else Color.Gray
                    )
                    if (selectedTab == "my_notes") {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Black)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Hộp lưu trữ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedTab == "archived") Color.Black else Color.Gray
                    )
                    if (selectedTab == "archived") {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Black)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Note Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Note Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "vjbniobnoib;jui",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "vjbniobnoib;jui",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Box {
                            IconButton(
                                onClick = { showNoteMenu = !showNoteMenu },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Menu",
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Popup menu
                            if (showNoteMenu) {
                                Card(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .width(120.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFE0E0E0),
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Archive",
                                                tint = Color.Black,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                "Lưu trữ",
                                                fontSize = 12.sp,
                                                color = Color.Black
                                            )
                                        }

                                        Divider(
                                            color = Color(0xFFE0E0E0),
                                            thickness = 1.dp,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = Color.Red,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                "Xoá",
                                                fontSize = 12.sp,
                                                color = Color.Red
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Chart placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawLine(
                                color = Color.Red,
                                start = Offset(size.width * 0.7f, size.height * 0.8f),
                                end = Offset(size.width * 0.95f, size.height * 0.2f),
                                strokeWidth = 3f
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { },
            containerColor = Color(0xFF1BC77D),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Note",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewNoteTakingScreen() {
    NoteTakingScreen()
}
