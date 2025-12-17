package com.study.domain.home.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.repository.TaskItemRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskItemRepository
) : BaseParamsUnsafeUseCase<String, Unit>() {
    override suspend fun execute(params: String) {
        repository.deleteTask(params)
    }
}
