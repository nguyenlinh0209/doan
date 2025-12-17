package com.study.home.ui.home.quizz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.domain.home.model.local.response.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizTakingScreen(
    questions: List<Question>,
    onNavigateBack: () -> Unit,
    onFinish: () -> Unit
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    // Track answers for each question (questionIndex to selectedAnswer)
    var answeredQuestions by remember { mutableStateOf(mutableMapOf<Int, String>()) }
    var showResultScreen by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentQuestionIndex]
    val selectedAnswer = answeredQuestions[currentQuestionIndex]
    val isAnswered = selectedAnswer != null

    // Helper function to get the correct answer from letter index
    fun getCorrectAnswer(question: Question): String {
        // Log để debug
        android.util.Log.d("QuizDebug", "Question: ${question.content}")
        android.util.Log.d("QuizDebug", "Answer letter: '${question.answer}'")
        android.util.Log.d("QuizDebug", "Options: ${question.options}")

        val answerIndex = when (question.answer.trim().uppercase()) {
            "A" -> 0
            "B" -> 1
            "C" -> 2
            "D" -> 3
            else -> {
                android.util.Log.d("QuizDebug", "Unknown answer format, defaulting to 0")
                0
            }
        }
        val correctAnswer = question.options.getOrNull(answerIndex) ?: question.options[0]
        android.util.Log.d(
            "QuizDebug",
            "Correct answer index: $answerIndex, value: '$correctAnswer'"
        )
        return correctAnswer
    }

    // Calculate correct count
    val correctCount = answeredQuestions.count { (index, answer) ->
        answer == getCorrectAnswer(questions[index])
    }

    // Show result screen if completed
    if (showResultScreen) {
        QuizResultScreen(
            correctCount = correctCount,
            totalQuestions = questions.size,
            onRetry = {
                currentQuestionIndex = 0
                answeredQuestions = mutableMapOf()
                showResultScreen = false
            },
            onFinish = onFinish
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$correctCount / ${questions.size}",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF26A69A)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Question number and content
            Text(
                text = currentQuestion.content,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Answer options
            currentQuestion.options.forEach { option ->
                AnswerOption(
                    text = option,
                    isSelected = selectedAnswer == option,
                    isCorrect = isAnswered && option == getCorrectAnswer(currentQuestion),
                    isWrong = isAnswered && selectedAnswer == option && option != getCorrectAnswer(
                        currentQuestion
                    ),
                    enabled = !isAnswered,
                    onClick = {
                        if (!isAnswered) {
                            answeredQuestions = answeredQuestions.toMutableMap().apply {
                                put(currentQuestionIndex, option)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Explanation section
            if (isAnswered) {
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(28.dp),
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xFF26A69A)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "✓",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Đáp án đúng",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentQuestion.explain,
                            fontSize = 14.sp,
                            color = Color(0xFF424242),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Navigation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Previous button
                if (currentQuestionIndex > 0) {
                    Button(
                        onClick = {
                            currentQuestionIndex--
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0E0E0),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Trước",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Next/Finish button
                Button(
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                        } else {
                            // Show result screen
                            showResultScreen = true
                        }
                    },
                    enabled = isAnswered,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF26A69A),
                        disabledContainerColor = Color(0xFFB0BEC5)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(
                        text = if (currentQuestionIndex < questions.size - 1) "Tiếp theo" else "Hoàn thành",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerOption(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isWrong: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect -> Color(0xFF26A69A)
        isWrong -> Color(0xFFEF5350)
        isSelected -> Color(0xFFE0F2F1)
        else -> Color.White
    }

    val borderColor = when {
        isCorrect || isWrong -> Color.Transparent
        isSelected -> Color(0xFF26A69A)
        else -> Color(0xFFE0E0E0)
    }

    val textColor = when {
        isCorrect || isWrong -> Color.White
        else -> Color.Black
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(12.dp),
        border = if (borderColor != Color.Transparent) {
            androidx.compose.foundation.BorderStroke(2.dp, borderColor)
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 15.sp,
                color = textColor,
                modifier = Modifier.weight(1f)
            )

            if (isCorrect || isWrong) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultScreen(
    correctCount: Int,
    totalQuestions: Int,
    onRetry: () -> Unit,
    onFinish: () -> Unit
) {
    val percentage = (correctCount.toFloat() / totalQuestions * 100).toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onFinish) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$correctCount / $totalQuestions",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF26A69A)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Circular Score Display
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(180.dp)
            ) {
                // Progress circle
                CircularProgressIndicator(
                    progress = { correctCount.toFloat() / totalQuestions },
                    modifier = Modifier.size(160.dp),
                    color = Color(0xFF26A69A),
                    strokeWidth = 12.dp,
                    trackColor = Color(0xFFE0E0E0)
                )

                // Score text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$correctCount/$totalQuestions",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF26A69A)
                    )
                    Text(
                        text = "ĐIỂM SỐ",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = when {
                    percentage >= 80 -> "Xuất sắc! 🎉"
                    percentage >= 60 -> "Làm tốt lắm! 👏"
                    percentage >= 40 -> "Khá tốt! 💪"
                    else -> "Cố gắng hơn nhé! 📚"
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Bạn đã hoàn thành bài kiểm tra",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF26A69A)
                    ),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF26A69A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Làm lại",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onFinish,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF26A69A)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Hoàn thành",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}