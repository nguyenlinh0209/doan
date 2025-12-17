package com.study.home.ui.home.quizz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.study.domain.home.model.local.Quizz

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizzScreen(
    uiState: QuizzUiState,
    onAction: (QuizzUiAction) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showQuizTaking by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var questionCount by remember { mutableStateOf("10") }
    var difficulty by remember { mutableStateOf("Vừa dụng") }

    val sheetState = rememberModalBottomSheetState()

    // Show quiz taking screen if there are generated questions
    if (showQuizTaking && uiState.archivedQuestion.isNotEmpty()) {
        QuizTakingScreen(
            questions = uiState.archivedQuestion,
            onNavigateBack = {
                showQuizTaking = false
            },
            onFinish = {
                showQuizTaking = false
                // You can add logic to save results here
            }
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onAction(QuizzUiAction.ClearQuestions) }) {
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
                onClick = {
                    showBottomSheet = true
                    title = ""
                    description = ""
                    questionCount = "10"
                    difficulty = "Vừa dụng"
                },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
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

            if (uiState.isGenerating) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(32.dp)
                            .wrapContentSize(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = Color(0xFF26A69A)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Đang tạo bài kiểm tra...",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }

    // Show quiz taking screen after generation completes
    LaunchedEffect(uiState.isGenerating, uiState.archivedQuestion) {
        if (!uiState.isGenerating && uiState.archivedQuestion.isNotEmpty()) {
            showQuizTaking = true
        }
    }

    // Bottom Sheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
            modifier = Modifier.fillMaxHeight(0.6f)
        ) {
            AddQuizzBottomSheet(
                title = title,
                onTitleChange = { title = it },
                description = description,
                onDescriptionChange = { description = it },
                questionCount = questionCount,
                onQuestionCountChange = { questionCount = it },
                difficulty = difficulty,
                onDifficultyChange = { difficulty = it },
                onCreateClick = {
                    onAction(QuizzUiAction.CreateQuizz(
                        title = title,
                        description = description,
                        questionCount = questionCount.toIntOrNull() ?: 10,
                    ))
                    showBottomSheet = false
                },
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}

@Composable
fun AddQuizzBottomSheet(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    questionCount: String,
    onQuestionCountChange: (String) -> Unit,
    difficulty: String,
    onDifficultyChange: (String) -> Unit,
    onCreateClick: () -> Unit,
    onDismiss: () -> Unit
) {
    var expandedQuestionCount by remember { mutableStateOf(false) }
    var expandedDifficulty by remember { mutableStateOf(false) }

    val questionCountOptions = listOf("5 câu", "10 câu", "20 câu", "40 câu")
    val difficultyOptions = listOf("Cơ bản", "Vừa dụng", "Nâng cao")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "Bài kiểm tra mới",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // AI Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F7F6), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color(0xFF26A69A),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "AI sẽ tự động tạo bài kiểm tra cho bạn",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF26A69A)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Hãy nhập chủ đề / nội dung bạn muốn học",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Title Input
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = { Text("Nhập chủ đề / nội dung", color = Color(0xFFCCCCCC)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF26A69A),
                unfocusedBorderColor = Color(0xFFEEEEEE),
                cursorColor = Color(0xFF26A69A)
            ),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Question Count and Difficulty Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Question Count
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Số câu hỏi",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box {
                    OutlinedButton(
                        onClick = { expandedQuestionCount = !expandedQuestionCount },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFF5F5F5),
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                    ) {
                        Text(questionCount, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expandedQuestionCount,
                        onDismissRequest = { expandedQuestionCount = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        questionCountOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, fontSize = 13.sp) },
                                onClick = {
                                    onQuestionCountChange(option.take(2))
                                    expandedQuestionCount = false
                                },
                                modifier = if (option.take(2) == questionCount) {
                                    Modifier.background(Color(0xFFE0F7F6))
                                } else {
                                    Modifier
                                }
                            )
                        }
                    }
                }
            }

            // Difficulty
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Độ khó",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box {
                    OutlinedButton(
                        onClick = { expandedDifficulty = !expandedDifficulty },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFF5F5F5),
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                    ) {
                        Text(difficulty, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expandedDifficulty,
                        onDismissRequest = { expandedDifficulty = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        difficultyOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, fontSize = 13.sp) },
                                onClick = {
                                    onDifficultyChange(option)
                                    expandedDifficulty = false
                                },
                                modifier = if (option == difficulty) {
                                    Modifier.background(Color(0xFFE0F7F6))
                                } else {
                                    Modifier
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Create Button
        Button(
            onClick = onCreateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF26A69A)
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Tạo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MyStudyContent(
    uiState: QuizzUiState,
    onAction: (QuizzUiAction) -> Unit
) {
    if (uiState.quizzes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chưa có bài quiz nào",
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
            items(uiState.quizzes) { quiz ->
                QuizzItem(
                    quiz = quiz,
                    onClick = { onAction(QuizzUiAction.SelectQuizz(quiz)) }
                )
            }
        }
    }
}

@Composable
fun ArchiveContent(
    uiState: QuizzUiState,
    onAction: (QuizzUiAction) -> Unit
) {
    if (uiState.archivedQuizzes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Không có quiz lưu trữ",
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
            items(uiState.archivedQuizzes) { quiz ->
                QuizzItem(
                    quiz = quiz,
                    onClick = { onAction(QuizzUiAction.SelectQuizz(quiz)) }
                )
            }
        }
    }
}

@Composable
fun QuizzItem(
    quiz: Quizz,
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
                text = quiz.topic,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${quiz.questionCount} câu hỏi",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}