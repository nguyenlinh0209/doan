package com.study.domain.user.usecase

import com.study.domain.user.model.User
import com.osprey.domain.user.model.request.LoginRequest
import com.study.domain.user.repository.UserRepository
import com.osprey.domain.base.BaseParamsUnsafeUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseParamsUnsafeUseCase<LoginRequest, User?>() {
    override suspend fun execute(params: LoginRequest): User? {
        return userRepository.login(params)
    }
}
