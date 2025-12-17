package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.TaskItem
import com.study.domain.home.repository.TaskItemRepository
import java.util.UUID
import javax.inject.Inject

class GetTasksByUserIdUseCase @Inject constructor(
    private val repository: TaskItemRepository
) : BaseParamsUnsafeUseCase<UUID, List<TaskItem>>() {
    override suspend fun execute(params: UUID): List<TaskItem> {
        return repository
            .getTasksByUserId(params.toString())
            .getOrDefault(emptyList())
    }
}
