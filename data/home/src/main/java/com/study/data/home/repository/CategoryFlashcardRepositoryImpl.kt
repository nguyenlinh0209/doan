package com.study.data.home.repository


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.study.domain.home.model.local.CategoryFlashcard
import com.study.domain.home.repository.CategoryFlashcardRepository
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class CategoryFlashcardRepositoryImpl @Inject constructor() :
    CategoryFlashcardRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val categoryCollection = firestore.collection("flashcard_categories")

    companion object {
        private const val TAG = "CategoryFlashcardRepo"
    }

    override suspend fun addCategory(category: CategoryFlashcard): CategoryFlashcard {
        categoryCollection
            .document(category.id.toString())
            .set(category.toMap())
            .await()

        Log.d(TAG, "Category saved: ${category.id}")
        return category
    }

    override suspend fun getAllCategories(): Result<List<CategoryFlashcard>> = try {
        val snapshot = categoryCollection.get().await()

        val categories = snapshot.documents.mapNotNull { doc ->
            try {
                CategoryFlashcard(
                    id = UUID.fromString(doc.getString("id")!!),
                    name = doc.getString("name").orEmpty()
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error mapping category", e)
                null
            }
        }

        Result.success(categories)
    } catch (e: Exception) {
        Log.e(TAG, "Error getting categories", e)
        Result.failure(e)
    }

    private fun CategoryFlashcard.toMap(): Map<String, Any> = mapOf(
        "id" to id.toString(),
        "name" to name
    )
}
