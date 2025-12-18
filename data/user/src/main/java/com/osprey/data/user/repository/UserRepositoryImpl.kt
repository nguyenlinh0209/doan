package com.osprey.data.user.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.osprey.data.common.datasource.AppSharePrefs
import com.osprey.data.user.datasource.remote.interceptor.AuthDataSource
import com.study.domain.user.model.User
import com.osprey.domain.user.model.request.LoginRequest
import com.study.domain.user.repository.UserRepository
import com.study.domain.user.usecase.UpdatePasswordRequest
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val appSharePrefs: AppSharePrefs,
) : UserRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")

    override suspend fun saveUserToFirebase(user: User): User? {
        return try {
            val firebaseUser = auth.currentUser ?: return null
            val uid = firebaseUser.uid

            val userEntity = mapOf(
                "id" to UUID.nameUUIDFromBytes(uid.toByteArray()).toString(),
                "email" to (user.email ?: firebaseUser.email),
                "name" to (user.name ?: ""),
                "classStudy" to user.classStudy,
                "isActive" to user.isActive,
                "createdAt" to user.createdAt,
                "updatedAt" to Date()
            )

            userCollection.document(uid).set(userEntity).await()
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return try {
            val snapshot = userCollection
                .whereEqualTo("email", email)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toUser()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getUserById(id: UUID): User? {
        return try {
            val snapshot = userCollection
                .whereEqualTo("id", id.toString())
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toUser()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override suspend fun updatePassword(
        request: UpdatePasswordRequest
    ): String {
        return try {
            val user = auth.currentUser ?: throw Exception("Người dùng không tồn tại")
            val email = user.email ?: throw Exception("Email không tồn tại")

            val credential = EmailAuthProvider.getCredential(email, request.currentPassword)
            user.reauthenticate(credential).await()

            user.updatePassword(request.newPassword).await()
            "Cập nhật mật khẩu thành công"
        } catch (e: Exception) {
            e.printStackTrace()
            when {
                e.message?.contains("The password is invalid") == true ->
                    throw Exception("Mật khẩu hiện tại không chính xác")
                e.message?.contains("A network error") == true ->
                    throw Exception("Lỗi kết nối mạng")
                else -> throw Exception(e.message ?: "Cập nhật mật khẩu thất bại")
            }
        }
    }

    override suspend fun getCurrentUser(): User? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val snapshot = userCollection.document(uid).get().await()
            if (snapshot.exists()) snapshot.toUser() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun updateUser(user: User): User? {
        val uid = auth.currentUser?.uid ?: return null

        return try {
            val updateData = mutableMapOf<String, Any?>(
                "name" to user.name,
                "classStudy" to user.classStudy,
                "updatedAt" to Date()
            )

            userCollection
                .document(uid)
                .update(updateData)
                .await()

            getCurrentUser()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCurrentUserUUID(): UUID? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val snapshot = userCollection.document(uid).get().await()
            snapshot.getString("id")?.let { UUID.fromString(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCurrentUserEmail(): String? {
        return authDataSource.getCurrentUser()?.email
    }

    override suspend fun login(request: LoginRequest): User? {
        return try {
            val loginResponse =
                authDataSource.login(request.username, request.password)

            if (loginResponse != null) {
                appSharePrefs.authToken = loginResponse.token
                getCurrentUser() ?: loginResponse.user
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun logout() {
        appSharePrefs.clearAuthData()
        auth.signOut()
    }

    fun DocumentSnapshot.toUser(): User? {
        return try {
            User(
                id = UUID.fromString(getString("id") ?: return null),
                email = getString("email"),
                name = getString("name"),
                classStudy = getLong("classStudy")?.toInt(),
                isActive = getBoolean("isActive") ?: false,
                createdAt = getDate("createdAt") ?: Date(),
                updatedAt = getDate("updatedAt") ?: Date(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
