package com.study.domain.home.usecase


import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.home.model.local.response.Question
import com.study.domain.home.repository.QuizzRepository
import javax.inject.Inject


data class GenerateQuizParams(
    val count: Int,
    val input: String
)


class GenerateQuizUseCase @Inject constructor(
    private val repository: QuizzRepository
) : BaseParamsUnsafeUseCase<GenerateQuizParams, List<Question>>() {
    override suspend fun execute(
        params: GenerateQuizParams
    ): List<Question> {
        return repository.generateQuiz(
            count = params.count,
            input = params.input
        )
    }
}
