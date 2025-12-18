package com.study.domain.home.usecase

import com.study.domain.home.repository.AIChatRepository
import com.wodox.domain.base.BaseParamsFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AsKAIResponseUseCase @Inject constructor(
    private val repository: AIChatRepository
) : BaseParamsFlowUseCase<String, String?>() {
    override suspend fun execute(params: String): Flow<String?> = flow {
        val response = repository.askAI(params)
        emit(response)
    }
}