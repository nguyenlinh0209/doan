package com.osprey.data.user.datasource.remote.interceptor

import com.study.domain.user.model.User
import com.osprey.domain.user.model.request.LoginResponse

interface AuthDataSource {
    suspend fun login(username: String, password: String): LoginResponse?

    suspend fun getCurrentUser(): User?

}
