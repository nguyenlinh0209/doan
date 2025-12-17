package com.osprey.data.user.datasource.remote.interceptor

import android.util.Log
import com.google.gson.Gson
import com.osprey.data.user.remote.AuthService
import com.study.domain.user.model.User
import com.osprey.domain.user.model.request.LoginRequest
import com.osprey.domain.user.model.request.LoginResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val openAIApi: AuthService,
    private val gson: Gson
) : AuthDataSource {
    override suspend fun login(username: String, password: String): LoginResponse? {
        return try {
            val request = LoginRequest(username, password)
            val response = openAIApi.login(request)
            Log.d("AuthDataSource", "Login Response => ${gson.toJson(response)}")
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCurrentUser(): User? {
        return try {
            openAIApi.getCurrentUser()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
