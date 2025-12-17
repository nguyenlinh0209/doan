package com.study.domain.user.usecase

import com.osprey.domain.base.BaseParamsUnsafeUseCase
import com.study.domain.user.model.User
import com.study.domain.user.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseParamsUnsafeUseCase<User, User?>() {
    override suspend fun execute(params: User): User? {
        return repository.saveUserToFirebase(params)
    }
}
