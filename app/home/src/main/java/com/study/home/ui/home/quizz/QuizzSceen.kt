package com.study.home.ui.home.quizz

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.domain.home.model.local.FlashCard
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiAction
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizzScreen(
    uiState: QuizzUiState,
    onAction: (QuizzUiAction) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onAction(QuizzUiAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF26A69A)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(QuizzUiAction.AddNewQuizz) },
                containerColor = Color(0xFF26A69A),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFF26A69A),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFF26A69A)
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            text = "Học tập của tôi",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 0) Color.Black else Color.Gray
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            text = "Hộp lưu trữ",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 1) Color.Black else Color.Gray
                        )
                    }
                )
            }

            // Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                when (selectedTab) {
                    0 -> MyStudyContent(uiState, onAction)
                    1 -> ArchiveContent(uiState, onAction)
                }
            }
        }
    }
}

@Composable
fun MyStudyContent(
    uiState: QuizzUiState,
    onAction: (QuizzUiAction) -> Unit
) {
    if (uiState.cards.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chưa có thẻ học nào",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.cards) { card ->
                FlashCardItem(
                    card = card,
                    onClick = { onAction(FlashCardUiAction.SelectCard(card)) }
                )
            }
        }
    }
}

@Composable
fun ArchiveContent(
    uiState: FlashCardUiState,
    onAction: (FlashCardUiAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Không có thẻ lưu trữ",
            color = Color.Gray,
            fontSize = 16.sp
        )
    }
}

@Composable
fun FlashCardItem(
    card: FlashCard,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = card.question,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = card.answer,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}