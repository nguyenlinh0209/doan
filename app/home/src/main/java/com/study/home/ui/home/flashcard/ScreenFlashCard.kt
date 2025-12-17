package com.study.home.ui.home.flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.domain.home.model.local.CategoryFlashcard
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import java.util.UUID

private val PrimaryGreen = Color(0xFF1BC77D)
private val BackgroundColor = Color(0xFFFAFAFA)
private val TextPrimary = Color(0xFF1F1F1F)
private val TextSecondary = Color(0xFF757575)

@Composable
fun ScreenFlashCard(
    uiState: ScreenFlashCardUiState,
    onAction: (ScreenFlashCardUiAction) -> Unit
) {
    var showCategoryDialog by remember { mutableStateOf(false) }
    var categoryName by remember { mutableStateOf("") }

    if (showCategoryDialog) {
        CreateCategoryDialog(
            categoryName = categoryName,
            onNameChange = { categoryName = it },
            onConfirm = {
                onAction(ScreenFlashCardUiAction.CreateCategory(categoryName))
                categoryName = ""
                showCategoryDialog = false
            },
            onDismiss = {
                categoryName = ""
                showCategoryDialog = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderSection(
                onBack = { onAction(ScreenFlashCardUiAction.OnBack) },
                onAddCategory = { showCategoryDialog = true }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Categories",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary,
                    modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.categories) { category ->
                        CategoryChip(
                            category = category,
                            onCategoryClick = { categoryId ->
                                onAction(ScreenFlashCardUiAction.SelectCategory(categoryId))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                EmptyFlashcardState(onAction)
            }
        }

        FloatingActionButton(
            onClick = { onAction(ScreenFlashCardUiAction.AddNewCard) },
            containerColor = PrimaryGreen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Card",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
private fun HeaderSection(
    onBack: () -> Unit,
    onAddCategory: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        IconButton(
            onClick = onBack,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextPrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Flashcards",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Learn efficiently",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        // Add Category Button
        IconButton(
            onClick = onAddCategory,
            modifier = Modifier.size(40.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = PrimaryGreen,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Category",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: CategoryFlashcard,
    onCategoryClick: (UUID) -> Unit
) {
    Surface(
        modifier = Modifier
            .height(40.dp)
            .clickable {
                category.id.let { onCategoryClick(it) }
            },
        shape = RoundedCornerShape(12.dp),
        color = PrimaryGreen.copy(alpha = 0.1f),
        border = BorderStroke(
            width = 1.dp,
            color = PrimaryGreen.copy(alpha = 0.3f),
        )
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                color = PrimaryGreen,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun EmptyFlashcardState(onAction: (ScreenFlashCardUiAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            onClick = { onAction(ScreenFlashCardUiAction.AddNewCard) },
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = PrimaryGreen.copy(alpha = 0.1f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier
                    .size(80.dp)
                    .padding(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No flashcards yet",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create your first flashcard to get started",
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun CreateCategoryDialog(
    categoryName: String,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = "New Category",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = TextPrimary
            )
        },
        text = {
            Column {
                TextField(
                    value = categoryName,
                    onValueChange = onNameChange,
                    placeholder = { Text("Enter category name", color = TextSecondary) },
                    label = { Text("Category name") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BackgroundColor,
                        unfocusedContainerColor = BackgroundColor,
                        focusedIndicatorColor = PrimaryGreen,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = categoryName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen,
                    disabledContainerColor = PrimaryGreen.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = TextSecondary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewScreenFlashCard() {
    ScreenFlashCard(
        uiState = ScreenFlashCardUiState(
            categories = listOf(
                CategoryFlashcard(name = "All"),
                CategoryFlashcard(name = "Work"),
                CategoryFlashcard(name = "Study"),
                CategoryFlashcard(name = "Languages")
            )
        ),
        onAction = {}
    )
}