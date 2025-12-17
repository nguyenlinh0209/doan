package com.osprey.data.user.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.osprey.data.common.datasource.AppSharePrefs
import com.osprey.data.user.datasource.remote.interceptor.AuthDataSource
import com.study.domain.user.model.User
import com.osprey.domain.user.model.request.LoginRequest
import com.study.domain.user.repository.UserRepository
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
            val currentUser = auth.currentUser ?: return null
            val uid = currentUser.uid

            val userEntity = mapOf(
                "id" to UUID.nameUUIDFromBytes(uid.toByteArray()).toString(),
                "email" to (user.email ?: currentUser.email),
                "name" to (user.name ?: ""),
                "isActive" to true,
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

            snapshot.documents.firstOrNull()?.let { doc ->
                User(
                    id = UUID.fromString(doc.getString("id") ?: ""),
                    email = doc.getString("email"),
                    name = doc.getString("name"),
                )
            }
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

            snapshot.documents.firstOrNull()?.let { doc ->
                User(
                    id = UUID.fromString(doc.getString("id") ?: ""),
                    email = doc.getString("email"),
                    name = doc.getString("name"),
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCurrentUserEmail(): String? {
        return try {
            val user = authDataSource.getCurrentUser()
            user?.email
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun logout() {
        try {
            appSharePrefs.clearAuthData()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun login(request: LoginRequest): User? {
        return try {
            val loginResponse = authDataSource.login(request.username, request.password)
            if (loginResponse != null) {
                appSharePrefs.authToken = loginResponse.token
                val currentUser = authDataSource.getCurrentUser()
                currentUser ?: loginResponse.user
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCurrentUserUUID(): UUID? {
        val currentUid = auth.currentUser?.uid ?: return null
        return try {
            val snapshot = userCollection.document(currentUid).get().await()
            snapshot.getString("id")?.let { UUID.fromString(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}