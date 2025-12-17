package com.study.home.ui.home.flashcard.managementflashcard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.model.local.response.FlashCardDetailResponse
import java.util.Date
import java.util.UUID


@Composable
fun FlashCardScreen(
    uiState: FlashCardUiState,
    onAction: (FlashCardUiAction) -> Unit
) {
    var selectedCard by remember { mutableStateOf<FlashCard?>(null) }
    var selectedCardIndex by remember { mutableStateOf(0) }
    var showCreateBottomSheet by remember { mutableStateOf(false) }
    var showStudyMode by remember { mutableStateOf(false) }

    val flashCards = listOf(
        FlashCard(
            id = UUID.randomUUID(),
            categoryId = UUID.randomUUID(),
            front = "Từ vựng Unit 1",
            back = "20 từ",
            created_at = Date()
        ),
        FlashCard(
            id = UUID.randomUUID(),
            categoryId = UUID.randomUUID(),
            front = "Cấu trúc câu tiếng Anh",
            back = "15 cấu trúc",
            created_at = Date()
        ),
        FlashCard(
            id = UUID.randomUUID(),
            categoryId = UUID.randomUUID(),
            front = "Động từ bất quy tắc",
            back = "30 động từ",
            created_at = Date()
        ),
        FlashCard(
            id = UUID.randomUUID(),
            categoryId = UUID.randomUUID(),
            front = "Thì hiện tại hoàn thành",
            back = "10 ví dụ",
            created_at = Date()
        ),
        FlashCard(
            id = UUID.randomUUID(),
            categoryId = UUID.randomUUID(),
            front = "Phát âm tiếng Anh",
            back = "25 âm",
            created_at = Date()
        )
    )

    // Show study mode khi có selected card
    if (selectedCard != null && showStudyMode) {
        FlashCardStudyScreen(
            generatedCard = FlashCardDetailResponse(
                front = selectedCard!!.front,
                back = selectedCard!!.back
            ),
            isGenerating = uiState.isGenerating,
            currentIndex = selectedCardIndex,
            totalCards = flashCards.size,
            onClose = {
                showStudyMode = false
                selectedCard = null
            },
            onSave = {
                showStudyMode = false
                selectedCard = null
                // Thêm card vào danh sách sau khi save
            },
            onPrevious = {
                if (selectedCardIndex > 0) {
                    selectedCardIndex--
                    selectedCard = flashCards[selectedCardIndex]
                }
            },
            onNext = {
                if (selectedCardIndex < flashCards.size - 1) {
                    selectedCardIndex++
                    selectedCard = flashCards[selectedCardIndex]
                }
            }
        )
    } else if (uiState.generatedFlashCard.isNotEmpty() && showStudyMode) {
        var generatedCardIndex by remember { mutableStateOf(0) }

        FlashCardStudyScreen(
            generatedCard = uiState.generatedFlashCard[generatedCardIndex],
            isGenerating = uiState.isGenerating,
            currentIndex = generatedCardIndex,
            totalCards = uiState.generatedFlashCard.size,
            onClose = {
                showStudyMode = false
            },
            onSave = {
                showStudyMode = false
                // Thêm card vào danh sách sau khi save
            },
            onPrevious = {
                if (generatedCardIndex > 0) {
                    generatedCardIndex--
                }
            },
            onNext = {
                if (generatedCardIndex < uiState.generatedFlashCard.size - 1) {
                    generatedCardIndex++
                }
            }
        )
    } else {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onAction(FlashCardUiAction.OnBack) },
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0xFF1BC77D), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        "Thẻ ghi nhớ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(flashCards.withIndex().toList()) { (index, card) ->
                        FlashCardItem(
                            card = card,
                            onClick = {
                                selectedCard = card
                                selectedCardIndex = index
                                showStudyMode = true
                            },
                            onEdit = { },
                            onDelete = { }
                        )
                    }
                }

                Button(
                    onClick = { showCreateBottomSheet = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1BC77D)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(0.dp, Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "New",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        "Thư mục mới",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        if (showCreateBottomSheet) {
            CreateFlashCardBottomSheet(
                isGenerating = uiState.isGenerating,
                onDismiss = { showCreateBottomSheet = false },
                onCreate = { input, countText, difficulty ->
                    val count = countText.replace(" câu", "").toIntOrNull() ?: 10
                    onAction(
                        FlashCardUiAction.GenerateFlashCard(
                            input = input,
                            count = count
                        )
                    )
                    showCreateBottomSheet = false
                    showStudyMode = true
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateFlashCardBottomSheet(
    isGenerating: Boolean,
    onDismiss: () -> Unit,
    onCreate: (front: String, back: String, difficulty: String) -> Unit
) {
    var frontText by remember { mutableStateOf("") }
    var backText by remember { mutableStateOf("") }
    var questionCount by remember { mutableStateOf("10") }
    var difficulty by remember { mutableStateOf("Vận dụng") }
    var showQuestionDropdown by remember { mutableStateOf(false) }
    var showDifficultyDropdown by remember { mutableStateOf(false) }

    val questionOptions = listOf("5 câu", "10 câu", "20 câu", "40 câu")
    val difficultyOptions = listOf("Cơ bản", "Vận dụng", "Nâng cao")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        scrimColor = Color.Black.copy(alpha = 0.32f),
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Bài kiểm tra mới",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "🤖",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    "AI sẽ tự động tạo bài kiểm tra cho bạn",
                    fontSize = 14.sp,
                    color = Color(0xFF1BC77D),
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                "Hãy nhập chủ đề / nội dung bạn muốn học",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            TextField(
                value = frontText,
                onValueChange = { frontText = it },
                placeholder = {
                    Text(
                        "Nhập chủ đề / nội dung",
                        color = Color(0xFFCCCCCC),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(bottom = 20.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF1BC77D),
                    unfocusedIndicatorColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Số câu hỏi",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Box {
                        Button(
                            onClick = { showQuestionDropdown = !showQuestionDropdown },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF0F0F0)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                questionCount,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Text("∨", color = Color.Black, fontSize = 16.sp)
                        }

                        DropdownMenu(
                            expanded = showQuestionDropdown,
                            onDismissRequest = { showQuestionDropdown = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            questionOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, fontSize = 14.sp) },
                                    onClick = {
                                        questionCount = option
                                        showQuestionDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Độ khó",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Box {
                        Button(
                            onClick = { showDifficultyDropdown = !showDifficultyDropdown },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF0F0F0)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(0.dp, Color.Transparent)
                        ) {
                            Text(
                                difficulty,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Text("∨", color = Color.Black, fontSize = 16.sp)
                        }

                        DropdownMenu(
                            expanded = showDifficultyDropdown,
                            onDismissRequest = { showDifficultyDropdown = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            difficultyOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, fontSize = 14.sp) },
                                    onClick = {
                                        difficulty = option
                                        showDifficultyDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    onCreate(frontText, backText, difficulty)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1BC77D)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = frontText.isNotEmpty() && !isGenerating
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 8.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Text(
                        "Đang tạo...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                } else {
                    Text(
                        "Tạo",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// Study Screen với animation lật thẻ
@Composable
fun FlashCardStudyScreen(
    generatedCard: FlashCardDetailResponse,
    isGenerating: Boolean,
    currentIndex: Int,
    totalCards: Int,
    onClose: () -> Unit,
    onSave: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    var isFlipped by remember { mutableStateOf(false) }
    var isMarked by remember { mutableStateOf(false) }
    var swipeProgress by remember { mutableStateOf(0f) }

    // Reset flip state khi chuyển card
    LaunchedEffect(currentIndex) {
        isFlipped = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, shape = CircleShape)
                        .shadow(4.dp, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF1BC77D),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    "Học thẻ ghi nhớ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                IconButton(
                    onClick = onSave,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1BC77D), shape = CircleShape)
                        .shadow(4.dp, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Progress indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${currentIndex + 1} / $totalCards",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF888888)
                )
            }

            // Flashcard với animation lật và navigation arrows
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                // Left Arrow
                IconButton(
                    onClick = onPrevious,
                    enabled = currentIndex > 0,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .size(48.dp)
                        .background(
                            color = if (currentIndex > 0) Color.White else Color.White.copy(0.5f),
                            shape = CircleShape
                        )
                        .shadow(8.dp, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous",
                        tint = if (currentIndex > 0) Color(0xFF1BC77D) else Color(0xFFCCCCCC),
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Flashcard
                FlashCardFlipAnimation(
                    front = generatedCard.front,
                    back = generatedCard.back,
                    isFlipped = isFlipped,
                    isMarked = isMarked,
                    onFlip = { isFlipped = !isFlipped },
                    onMark = { isMarked = !isMarked }
                )

                // Right Arrow
                IconButton(
                    onClick = onNext,
                    enabled = currentIndex < totalCards - 1,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .size(48.dp)
                        .background(
                            color = if (currentIndex < totalCards - 1) Color.White else Color.White.copy(0.5f),
                            shape = CircleShape
                        )
                        .shadow(8.dp, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        tint = if (currentIndex < totalCards - 1) Color(0xFF1BC77D) else Color(0xFFCCCCCC),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Navigation Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        isFlipped = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFDDDDDD),
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Reset",
                        tint = Color(0xFF1BC77D),
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 6.dp)
                    )
                    Text(
                        "Reset",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = { isFlipped = !isFlipped },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1BC77D)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Flip",
                        tint = Color.White,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 6.dp)
                    )
                    Text(
                        "Lật thẻ",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (isGenerating) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF1BC77D),
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        "Đang tạo thẻ...",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FlashCardFlipAnimation(
    front: String,
    back: String,
    isFlipped: Boolean,
    isMarked: Boolean,
    onFlip: () -> Unit,
    onMark: () -> Unit
) {
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "flip"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .aspectRatio(0.65f)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .shadow(12.dp, shape = RoundedCornerShape(24.dp))
                .graphicsLayer {
                    this.rotationY = rotationY
                    cameraDistance = 8 * density
                }
                .clickable { onFlip() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isFlipped) Color(0xFF1BC77D) else Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .graphicsLayer {
                        this.rotationY = if (isFlipped) 180f else 0f
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        if (isFlipped) "Câu trả lời" else "Câu hỏi",
                        fontSize = 12.sp,
                        color = if (isFlipped) Color.White else Color(0xFF888888),
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        if (isFlipped) back else front,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isFlipped) Color.White else Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Text(
                        "Nhấp để xem ${if (isFlipped) "câu hỏi" else "câu trả lời"}",
                        fontSize = 12.sp,
                        color = if (isFlipped) Color.White.copy(0.7f) else Color(0xFFAAAAAA),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        IconButton(
            onClick = onMark,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(44.dp)
                .background(
                    color = if (isMarked) Color(0xFFFFEB3B) else Color.White,
                    shape = CircleShape
                )
                .shadow(4.dp, shape = CircleShape)
        ) {
            Icon(
                imageVector = if (isMarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Mark",
                tint = Color(0xFFFF6B6B),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun FlashCardItem(
    card: FlashCard,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    card.front,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                if (card.back.isNotEmpty()) {
                    Text(
                        card.back,
                        fontSize = 13.sp,
                        color = Color(0xFFAAAAAA)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 12.dp)
            ) {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFF1BC77D),
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF6B6B),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Preview(
    name = "CategoryFlashCardRepository Screen",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun PreviewCategoryFlashCardScreen() {
    val initial = FlashCardUiState()
    MaterialTheme {
        Surface {
            FlashCardScreen(
                uiState = initial,
                onAction = {}
            )
        }
    }
}