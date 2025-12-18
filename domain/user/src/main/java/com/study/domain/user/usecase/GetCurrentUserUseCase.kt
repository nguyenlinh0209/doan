package com.study.domain.user.usecase

import com.osprey.domain.base.BaseNoParamsUnsafeUseCase
import com.study.domain.user.model.User
import com.study.domain.user.repository.UserRepository
import javax.inject.Inject


class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseNoParamsUnsafeUseCase<User?>() {
    override suspend fun execute(): User? {
        return userRepository.getCurrentUser()
    }
}