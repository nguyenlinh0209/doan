package com.study.calendar.ui

import com.study.domain.home.model.local.TaskItem

sealed class CalendarUiAction {
    data class SaveTaskItem(val taskItem: TaskItem) : CalendarUiAction()
    data class ToggleTaskCompleted(val task: TaskItem) : CalendarUiAction()
    data class DeleteTask(val task: TaskItem) : CalendarUiAction()
    data class EditTaskTitle(val task: TaskItem, val newTitle: String) : CalendarUiAction()
    data class EditTaskDescription(val task: TaskItem, val newDescription: String) : CalendarUiAction()
}
