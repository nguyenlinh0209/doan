package com.osprey.data.user.remote

import com.study.domain.user.model.User
import com.osprey.domain.user.model.request.LoginRequest
import com.osprey.domain.user.model.request.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthService{
    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("api/auth/user")
    suspend fun getCurrentUser(): User

}