package com.study.domain.user.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.user.repository.UserRepository
import javax.inject.Inject

data class UpdatePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)

class UpdatePasswordUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseParamsUnsafeUseCase<UpdatePasswordRequest, String?>() {

    override suspend fun execute(params: UpdatePasswordRequest): String? {
        return repository.updatePassword(params)
    }
}
