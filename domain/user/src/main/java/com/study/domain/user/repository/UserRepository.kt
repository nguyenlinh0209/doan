package com.study.domain.user.repository

import com.study.domain.user.model.User
import com.osprey.domain.user.model.request.LoginRequest
import java.util.UUID

interface UserRepository {
    suspend fun saveUserToFirebase(user: User): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun getUserById(id: UUID): User?
    suspend fun getCurrentUserEmail(): String?

    suspend fun logout()

    suspend fun login(request: LoginRequest): User?

    suspend fun getCurrentUserUUID(): UUID?


}