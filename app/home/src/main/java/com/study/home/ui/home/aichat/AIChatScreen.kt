package com.study.home.ui.home.aichat

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.osprey.domain.common.model.Sender
import com.osprey.domain.remote.model.request.ChatMessage
import com.study.home.R

@Composable
fun AIChatScreen(
    state: AIChatUiState,
    onAction: (AIChatUiAction) -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onAction(AIChatUiAction.Close)},
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_ai),
                    contentDescription = "AI Logo",
                    tint = Color(0xFF4A90E2),
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "Gia sư AI",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "History",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            // Messages Area
            if (state.messages.isEmpty() && !state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tôi có thể giúp gì cho bạn ?",
                        fontSize = 18.sp,
                        color = Color(0xFFBBBBBB),
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(state.messages.size) { index ->
                        val message = state.messages[index]
                        MessageBubble(message = message)
                    }

                    // Loading indicator
                    if (state.isLoading) {
                        item {
                            ThinkingAnimation()
                        }
                    }

                    // Error message
                    if (!state.error.isNullOrBlank()) {
                        item {
                            ErrorBubble(error = state.error ?: "")
                        }
                    }
                }
            }

            // Message Input Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Image",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = {
                            Text(
                                "Hỏi bất cứ điều gì",
                                color = Color(0xFFBBBBBB),
                                fontSize = 14.sp
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank() && !state.isLoading) {
                                onAction(AIChatUiAction.SendMessage(messageText))
                                messageText = ""
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (state.isLoading) Color(0xFFCCCCCC) else Color(0xFF4A90E2),
                                shape = CircleShape
                            ),
                        enabled = !state.isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val isUser = message.sender == Sender.USER
    val messageText = message.content.firstOrNull()?.text ?: ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Icon(
                painter = painterResource(id = com.study.home.R.drawable.ic_ai),
                contentDescription = "AI",
                tint = Color(0xFF4A90E2),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Top)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (isUser) Color(0xFF4A90E2) else Color(0xFFF0F4FF),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = messageText,
                fontSize = 14.sp,
                color = if (isUser) Color.White else Color.Black,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User",
                tint = Color(0xFF4A90E2),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Top)
            )
        }
    }
}

@Composable
fun ThinkingAnimation() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = com.study.home.R.drawable.ic_ai),
            contentDescription = "AI",
            tint = Color(0xFF4A90E2),
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Top)
        )
        Spacer(modifier = Modifier.width(8.dp))

        val infiniteTransition = rememberInfiniteTransition(label = "thinking")
        val dotScale1 by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = EaseInOutQuad),
                repeatMode = RepeatMode.Reverse
            ),
            label = "dot1"
        )
        val dotScale2 by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = EaseInOutQuad, delayMillis = 150),
                repeatMode = RepeatMode.Reverse
            ),
            label = "dot2"
        )
        val dotScale3 by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = EaseInOutQuad, delayMillis = 300),
                repeatMode = RepeatMode.Reverse
            ),
            label = "dot3"
        )

        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFF0F4FF),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                repeat(3) { index ->
                    val scale = when (index) {
                        0 -> dotScale1
                        1 -> dotScale2
                        else -> dotScale3
                    }
                    Box(
                        modifier = Modifier
                            .size(10.dp * scale)
                            .background(
                                color = Color(0xFF4A90E2),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorBubble(error: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFEBEE),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Error",
            tint = Color(0xFFE53935),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = error,
            fontSize = 14.sp,
            color = Color(0xFFE53935)
        )
    }
}