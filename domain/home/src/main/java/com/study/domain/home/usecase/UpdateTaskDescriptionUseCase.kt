package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.TaskItem
import com.study.domain.home.repository.TaskItemRepository
import javax.inject.Inject

data class UpdateTaskDescriptionParams(
    val taskId: String,
    val taskItem: TaskItem,
    val newDescription: String
)

class UpdateTaskDescriptionUseCase @Inject constructor(
    private val repository: TaskItemRepository
) : BaseParamsUnsafeUseCase<UpdateTaskDescriptionParams, Unit>() {
    override suspend fun execute(params: UpdateTaskDescriptionParams) {
        val updatedTask = params.taskItem.copy(
            description = params.newDescription
        )
        repository.updateTask(
            taskId = params.taskId,
            taskItem = updatedTask
        )
    }
}
