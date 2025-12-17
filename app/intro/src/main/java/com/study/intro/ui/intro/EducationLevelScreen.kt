package com.study.intro.ui.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EducationFlowScreen(
    onNavigateBack: () -> Unit,
    onComplete: (String, String) -> Unit
) {
    var currentStep by remember { mutableStateOf(EducationStep.LEVEL) }
    var selectedLevel by remember { mutableStateOf<String?>(null) }

    when (currentStep) {
        EducationStep.LEVEL -> {
            EducationLevelScreen(
                onNavigateBack = onNavigateBack,
                onContinue = { level ->
                    selectedLevel = level
                    currentStep = EducationStep.GRADE
                }
            )
        }
        EducationStep.GRADE -> {
            GradeSelectionScreen(
                educationLevel = selectedLevel ?: "THCS",
                onNavigateBack = {
                    currentStep = EducationStep.LEVEL
                },
                onComplete = { level, grade ->
                    onComplete(level, grade)
                }
            )
        }
    }
}

enum class EducationStep {
    LEVEL,
    GRADE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationLevelScreen(
    onNavigateBack: () -> Unit,
    onContinue: (String) -> Unit
) {
    var selectedLevel by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
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
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Vui lòng cho chúng tôi biết về chương trình bạn đang theo học",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Chúng tôi sẽ chuẩn bị nội dung phù hợp dựa trên thông tin của bạn",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            EducationOption(
                label = "THCS",
                isSelected = selectedLevel == "THCS",
                onClick = { selectedLevel = "THCS" }
            )

            Spacer(modifier = Modifier.height(16.dp))

            EducationOption(
                label = "THPT",
                isSelected = selectedLevel == "THPT",
                onClick = { selectedLevel = "THPT" }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    selectedLevel?.let { onContinue(it) }
                },
                enabled = selectedLevel != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF26A69A),
                    disabledContainerColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Tiếp theo",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeSelectionScreen(
    educationLevel: String,
    onNavigateBack: () -> Unit,
    onComplete: (String, String) -> Unit
) {
    var selectedGrade by remember { mutableStateOf<String?>(null) }

    val grades = when (educationLevel) {
        "THCS" -> listOf("Lớp 6", "Lớp 7", "Lớp 8", "Lớp 9")
        "THPT" -> listOf("Lớp 10", "Lớp 11", "Lớp 12")
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
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
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Vui lòng cho chúng tôi biết thông tin lớp của bạn",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Chúng tôi sẽ chuẩn bị nội dung phù hợp dựa trên thông tin của bạn",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            grades.forEach { grade ->
                EducationOption(
                    label = grade,
                    isSelected = selectedGrade == grade,
                    onClick = { selectedGrade = grade }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    selectedGrade?.let { grade ->
                        onComplete(educationLevel, grade)
                    }
                },
                enabled = selectedGrade != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF26A69A),
                    disabledContainerColor = Color(0xFFE0E0E0)
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

@Composable
fun EducationOption(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = if (isSelected) Color(0xFF26A69A) else Color(0xFFE0E0E0),
                    shape = CircleShape
                )
                .background(
                    color = if (isSelected) Color.White else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = Color(0xFF26A69A),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}