package com.study.domain.user.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.user.model.User
import com.study.domain.user.repository.UserRepository
import java.util.UUID
import javax.inject.Inject



class GetUserById @Inject constructor(
    private val userRepository: UserRepository
) : BaseParamsUnsafeUseCase<UUID, User?>() {
    override suspend fun execute(params: UUID): User? {
        return userRepository.getUserById(params)
    }
}