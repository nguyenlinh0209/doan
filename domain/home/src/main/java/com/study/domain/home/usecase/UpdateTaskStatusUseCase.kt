package com.study.domain.home.usecase


import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.repository.TaskItemRepository
import javax.inject.Inject

data class UpdateTaskStatusParams(
    val taskId: String,
    val isCompleted: Boolean
)

class UpdateTaskStatusUseCase @Inject constructor(
    private val repository: TaskItemRepository
) : BaseParamsUnsafeUseCase<UpdateTaskStatusParams, Unit>() {
    override suspend fun execute(params: UpdateTaskStatusParams) {
        repository.updateTaskStatus(
            taskId = params.taskId,
            isCompleted = params.isCompleted
        )
    }
}
