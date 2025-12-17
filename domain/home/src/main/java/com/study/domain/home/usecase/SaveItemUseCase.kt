package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.TaskItem
import com.study.domain.home.repository.TaskItemRepository
import javax.inject.Inject


class SaveItemUseCase @Inject constructor(
    private val repository: TaskItemRepository
) : BaseParamsUnsafeUseCase<TaskItem, TaskItem?>() {
    override suspend fun execute(params: TaskItem): TaskItem {
        return repository.addTask(params)
    }
}
