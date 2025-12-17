package com.study.data.home.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.study.domain.home.model.local.TaskItem
import com.study.domain.home.repository.TaskItemRepository
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class TaskItemRepositoryImpl @Inject constructor() : TaskItemRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val tasksCollection = firestore.collection("tasks")
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    companion object {
        private const val TAG = "TaskItemRepository"
    }

    override suspend fun addTask(taskItem: TaskItem): TaskItem {
        tasksCollection
            .document(taskItem.id.toString())
            .set(taskItem.toMap())
            .await()

        Log.d(TAG, "Task saved: ${taskItem.id}")
        return taskItem
    }

    override suspend fun getTasksByUserId(userId: String): Result<List<TaskItem>> = try {

        val snapshot = tasksCollection
            .whereEqualTo("user_id", userId)
            .get()
            .await()

        Log.d(TAG, "Firestore snapshot size = ${snapshot.size()}")

        val tasks = snapshot.documents.mapNotNull { doc ->
            try {
                TaskItem(
                    id = UUID.fromString(doc.getString("id")!!),
                    dateTime = LocalDateTime.parse(
                        doc.getString("dateTime")!!,
                        dateTimeFormatter
                    ),
                    title = doc.getString("title").orEmpty(),
                    description = doc.getString("description").orEmpty(),
                    color = doc.getLong("color") ?: 0xFF1BC77D,
                    isCompleted = doc.getBoolean("isCompleted") ?: false,
                    userId = doc.getString("user_id")?.let { UUID.fromString(it) }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error mapping task", e)
                null
            }
        }

        Result.success(tasks)

    } catch (e: Exception) {
        Log.e(TAG, "Error getting tasks by userId", e)
        Result.failure(e)
    }

    override suspend fun updateTask(taskId: String, taskItem: TaskItem): Result<Unit> = try {
        tasksCollection.document(taskId).set(taskItem.toMap()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateTaskStatus(
        taskId: String,
        isCompleted: Boolean
    ): Result<Unit> = try {
        tasksCollection.document(taskId)
            .update("isCompleted", isCompleted)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> = try {
        tasksCollection.document(taskId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteAllTasks(): Result<Unit> = try {
        val snapshot = tasksCollection.get().await()
        val batch = firestore.batch()
        snapshot.documents.forEach { batch.delete(it.reference) }
        batch.commit().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun TaskItem.toMap(): Map<String, Any> = mapOf(
        "id" to id.toString(),
        "user_id" to userId.toString(),
        "dateTime" to dateTime.format(dateTimeFormatter),
        "title" to title,
        "description" to description,
        "color" to color, // ✅ Long
        "isCompleted" to isCompleted
    )
}
