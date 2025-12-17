package com.study.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.osprey.domain.home.model.local.CalendarDayData
import com.study.domain.home.model.local.TaskItem
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.UUID

@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    onAction: (CalendarUiAction) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val tasksByDate = remember(uiState.tasks) {
        uiState.tasks.groupBy {
            it.dateTime.toLocalDate()
        }
    }

    val dayHeaders = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")
    val calendarDays = generateCalendarDays(currentMonth)
    val tasksForSelectedDate = tasksByDate[selectedDate] ?: emptyList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    when {
                        dragAmount > 50 -> currentMonth = currentMonth.minusMonths(1)
                        dragAmount < -50 -> currentMonth = currentMonth.plusMonths(1)
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                    Icon(Icons.Default.ChevronLeft, "Previous month")
                }

                Text(
                    "${currentMonth.month.value}/${currentMonth.year}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                    Icon(Icons.Default.ChevronRight, "Next month")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                dayHeaders.forEach { day ->
                    Text(
                        day,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFCCCCCC),
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                calendarDays.chunked(7).forEach { week ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        week.forEach { day ->
                            DayCell(
                                day = day,
                                isSelected = selectedDate == day.localDate,
                                hasEvent = tasksByDate.containsKey(day.localDate),
                                onClick = {
                                    if (day.isCurrentMonth) {
                                        selectedDate = day.localDate
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Text(
                "Ngày ${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                if (tasksForSelectedDate.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Không có task nào",
                                fontSize = 14.sp,
                                color = Color(0xFFAAAAAA)
                            )
                        }
                    }
                } else {
                    items(tasksForSelectedDate) { task ->
                        TaskItemCard(task, onAction)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color(0xFF1BC77D),
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Thêm sự kiện",
                modifier = Modifier.size(28.dp)
            )
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Thêm sự kiện") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        TextField(
                            value = eventTitle,
                            onValueChange = { eventTitle = it },
                            label = { Text("Tên sự kiện") },
                            singleLine = true
                        )

                        TextField(
                            value = eventDescription,
                            onValueChange = { eventDescription = it },
                            label = { Text("Mô tả") },
                            maxLines = 3
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (eventTitle.isNotBlank()) {
                                onAction(
                                    CalendarUiAction.SaveTaskItem(
                                        TaskItem(
                                            id = UUID.randomUUID(),
                                            dateTime = LocalDateTime.now(),
                                            title = eventTitle,
                                            description = eventDescription,
                                            color = 0xFF2EDB8F,
                                            isCompleted = false,
                                        )
                                    )
                                )
                                eventTitle = ""
                                eventDescription = ""
                                showAddDialog = false
                            }
                        }
                    ) {
                        Text("Lưu")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Hủy")
                    }
                }
            )
        }
    }
}

private fun generateCalendarDays(yearMonth: YearMonth): List<CalendarDayData> {
    val firstDay = yearMonth.atDay(1)
    val lastDay = yearMonth.atEndOfMonth()
    val firstDayOfWeek = firstDay.dayOfWeek.value % 7

    val days = mutableListOf<CalendarDayData>()

    // Add days from previous month
    val prevMonth = yearMonth.minusMonths(1)
    val prevMonthLastDay = prevMonth.atEndOfMonth()
    for (i in firstDayOfWeek - 1 downTo 0) {
        days.add(
            CalendarDayData(
                localDate = prevMonthLastDay.minusDays(i.toLong()),
                day = (prevMonthLastDay.dayOfMonth - i).toString(),
                isCurrentMonth = false
            )
        )
    }

    // Add days from current month
    for (i in 1..lastDay.dayOfMonth) {
        days.add(
            CalendarDayData(
                localDate = firstDay.plusDays((i - 1).toLong()),
                day = i.toString(),
                isCurrentMonth = true
            )
        )
    }

    // Add days from next month to fill grid
    val remainingCells = 42 - days.size
    for (i in 1..remainingCells) {
        days.add(
            CalendarDayData(
                localDate = yearMonth.plusMonths(1).atDay(i),
                day = i.toString(),
                isCurrentMonth = false
            )
        )
    }

    return days
}

@Composable
private fun DayCell(
    day: CalendarDayData,
    isSelected: Boolean,
    hasEvent: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .clickable(enabled = day.isCurrentMonth) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = when {
                        isSelected -> Color(0xFF1BC77D)
                        !day.isCurrentMonth -> Color.Transparent
                        else -> Color.Transparent
                    },
                    shape = CircleShape
                )
                .border(
                    width = if (hasEvent && !isSelected) 2.dp else 0.dp,
                    color = Color(0xFF1BC77D),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                day.day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = when {
                    isSelected -> Color.White
                    !day.isCurrentMonth -> Color(0xFFDDDDDD)
                    else -> Color.Black
                }
            )
        }

        if (hasEvent && !isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(Color(0xFF1BC77D), shape = CircleShape)
            )
        }
    }
}

@Composable
private fun TaskItemCard(
    task: TaskItem,
    onAction: (CalendarUiAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showEditTitle by remember { mutableStateOf(false) }
    var showEditDesc by remember { mutableStateOf(false) }
    var editText by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (task.isCompleted) Color(0xFF1BC77D) else Color.LightGray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onAction(CalendarUiAction.ToggleTaskCompleted(task))
                    }
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    task.title,
                    textDecoration = if (task.isCompleted)
                        TextDecoration.LineThrough else TextDecoration.None,
                    fontWeight = FontWeight.SemiBold
                )
                if (task.description.isNotBlank()) {
                    Text(task.description, fontSize = 12.sp, color = Color.Gray)
                }
            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit title") },
                        onClick = {
                            editText = task.title
                            showEditTitle = true
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Edit description") },
                        onClick = {
                            editText = task.description
                            showEditDesc = true
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete", color = Color.Red) },
                        onClick = {
                            expanded = false
                            onAction(CalendarUiAction.DeleteTask(task))
                        }
                    )
                }
            }
        }
    }

    if (showEditTitle || showEditDesc) {
        AlertDialog(
            onDismissRequest = {
                showEditTitle = false
                showEditDesc = false
            },
            title = { Text("Edit") },
            text = {
                TextField(
                    value = editText,
                    onValueChange = { editText = it }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (showEditTitle)
                        onAction(CalendarUiAction.EditTaskTitle(task, editText))
                    else
                        onAction(CalendarUiAction.EditTaskDescription(task, editText))
                    showEditTitle = false
                    showEditDesc = false
                }) { Text("Save") }
            }
        )
    }
}


@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 360,
    heightDp = 640
)
@Composable
fun PreviewCalendarScreen() {
    val initialState = CalendarUiState()
    MaterialTheme {
        Surface {
            CalendarScreen(
                uiState = initialState,
                onAction = {}
            )
        }
    }
}