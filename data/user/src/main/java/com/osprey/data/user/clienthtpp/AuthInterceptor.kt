package com.osprey.data.user.clienthtpp

import android.util.Log
import com.osprey.data.common.datasource.AppSharePrefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val appSharePrefs: AppSharePrefs
) : Interceptor {
    companion object {
        private const val TAG = "AuthInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = appSharePrefs.authToken

        Log.d(TAG, "=== REQUEST INFO ===")
        Log.d(TAG, "URL: ${originalRequest.url}")
        Log.d(TAG, "Method: ${originalRequest.method}")
        Log.d(TAG, "Token exists: ${!token.isNullOrEmpty()}")

        val isLoginRequest = originalRequest.url.encodedPath.contains("api/auth/login")
        Log.d(TAG, "Is login request: $isLoginRequest")

        val newRequest = if (!token.isNullOrEmpty() && !isLoginRequest) {
            Log.d(TAG, "Adding X-Authorization header")
            originalRequest.newBuilder()
                .header("X-Authorization", "Bearer $token")
                .build()
        } else {
            Log.d(TAG, "Skip adding header (token empty or login request)")
            originalRequest
        }

        return try {
            val response = chain.proceed(newRequest)

            Log.d(TAG, "=== RESPONSE INFO ===")
            Log.d(TAG, "Status Code: ${response.code}")
            Log.d(TAG, "Status Message: ${response.message}")

            response
        } catch (e: Exception) {
            Log.e(TAG, "=== ERROR ===")
            Log.e(TAG, "Error message: ${e.message}")
            Log.e(TAG, "Error type: ${e.javaClass.simpleName}")
            e.printStackTrace()
            throw e
        }
    }
}