package com.study.domain.user.usecase

import com.osprey.domain.base.BaseNoParamsUnsafeUseCase
import com.study.domain.user.repository.UserRepository
import java.util.UUID
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseNoParamsUnsafeUseCase<UUID?>() {
    override suspend fun execute(): UUID? {
        return userRepository.getCurrentUserUUID()
    }
}
