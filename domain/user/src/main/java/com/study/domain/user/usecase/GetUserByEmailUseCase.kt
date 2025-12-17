package com.study.domain.user.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.user.model.User
import com.study.domain.user.repository.UserRepository
import javax.inject.Inject


class GetUserByEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseParamsUnsafeUseCase<String, User?>() {
    override suspend fun execute(params: String): User? {
        return userRepository.getUserByEmail(params)
    }
}
