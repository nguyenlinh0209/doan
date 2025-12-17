package com.study.home.ui.home.profile.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.core.theme.AppStrings.items
import com.study.data.home.HistoryItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
@Composable
fun HistoryScreen() {
    var selectedFilter by remember { mutableStateOf("Tất cả") }
    var searchText by remember { mutableStateOf("") }

    val filters = listOf("Tất cả", "Bài quiz", "Hỏi AI", "Scan ảnh", "Flash card")

    val historyItems = listOf(
        HistoryItem(1, "", "10:30")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with back button
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    placeholder = {
                        Text(
                            "Tìm kiếm bài học cũ...",
                            fontSize = 14.sp,
                            color = Color(0xFFAAAAAA)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filter Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters.size) { index ->
                    val filter = filters[index]
                    val isSelected = filter == selectedFilter

                    Button(
                        onClick = { selectedFilter = filter },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color.Black else Color.White
                        ),
                        border = if (!isSelected) BorderStroke(
                            width = 1.dp,
                            color = Color(0xFFE0E0E0)
                        ) else null,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(36.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
                    ) {
                        Text(
                            filter,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Hôm nay",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // History Items List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(historyItems.size) { index ->
                    HistoryItemCard(historyItems[index])
                }

                // Add more items for demonstration
                items(3) { index ->
                    HistoryItemCard(
                        HistoryItem(
                            id = index + 2,
                            title = "",
                            time = "${9 + index}:${30 - index * 10}"
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(item: HistoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left content
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Middle content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.title.ifEmpty { "Bài học" },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            // Right content with time and chevron
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    item.time,
                    fontSize = 12.sp,
                    color = Color(0xFFAAAAAA),
                    fontWeight = FontWeight.Normal
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next",
                    tint = Color(0xFFAAAAAA),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 360,
    heightDp = 640
)
@Composable
fun PreviewHistoryScreenLight() {
    MaterialTheme {
        Surface {
            HistoryScreen()
        }
    }
}