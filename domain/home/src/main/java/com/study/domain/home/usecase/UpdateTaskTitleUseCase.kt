package com.study.domain.home.usecase


import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.TaskItem
import com.study.domain.home.repository.TaskItemRepository
import javax.inject.Inject

data class UpdateTaskTitleParams(
    val taskId: String,
    val taskItem: TaskItem,
    val newTitle: String
)

class UpdateTaskTitleUseCase @Inject constructor(
    private val repository: TaskItemRepository
) : BaseParamsUnsafeUseCase<UpdateTaskTitleParams, Unit>() {
    override suspend fun execute(params: UpdateTaskTitleParams) {
        val updatedTask = params.taskItem.copy(
            title = params.newTitle
        )
        repository.updateTask(
            taskId = params.taskId,
            taskItem = updatedTask
        )
    }
}
