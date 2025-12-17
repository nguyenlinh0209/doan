package com.study.domain.home.repository

import com.study.domain.home.model.local.TaskItem

interface TaskItemRepository {
    suspend fun addTask(taskItem: TaskItem): TaskItem

    suspend fun getTasksByUserId(userId: String): Result<List<TaskItem>>

    suspend fun updateTask(taskId: String, taskItem: TaskItem): Result<Unit>
    suspend fun updateTaskStatus(taskId: String, isCompleted: Boolean): Result<Unit>

    suspend fun deleteTask(taskId: String): Result<Unit>
    suspend fun deleteAllTasks(): Result<Unit>
}
