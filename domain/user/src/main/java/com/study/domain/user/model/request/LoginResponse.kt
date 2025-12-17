package com.osprey.domain.user.model.request

import com.study.domain.user.model.User

data class LoginResponse(
    val token: String,
    val user: User
)