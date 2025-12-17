package com.study.data.home.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.study.data.home.datasource.remote.datasource.AIChatDataSource
import com.study.domain.home.model.local.FlashCard
import com.study.domain.home.model.local.response.FlashCardDetailResponse
import com.study.domain.home.repository.FlashCardRepository
import com.study.common.util.PromptUtils
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class FlashCardRepositoryImpl @Inject constructor(
    private val aiChatDataSource: AIChatDataSource,
) : FlashCardRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val flashCardCollection = firestore.collection("flashcards")

    companion object {
        private const val TAG = "FlashCardRepo"
    }

    override suspend fun saveFlashCard(flashCard: FlashCard): FlashCard {
        flashCardCollection
            .document(flashCard.id.toString())
            .set(flashCard.toMap())
            .await()

        Log.d(TAG, "FlashCard saved: ${flashCard.id}")
        return flashCard
    }

    override suspend fun getAllFlashCardByCategoryId(
        categoryId: UUID
    ): Result<List<FlashCard>> = try {

        val snapshot = flashCardCollection
            .whereEqualTo("categoryId", categoryId.toString())
            .get()
            .await()

        val flashCards = snapshot.documents.mapNotNull { doc ->
            try {
                FlashCard(
                    id = UUID.fromString(doc.getString("id")!!),
                    categoryId = UUID.fromString(doc.getString("categoryId")!!),
                    front = doc.getString("front").orEmpty(),
                    back = doc.getString("back").orEmpty(),
                    created_at = doc.getDate("created_at") ?: Date()
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error mapping flashcard", e)
                null
            }
        }

        Result.success(flashCards)

    } catch (e: Exception) {
        Log.e(TAG, "Error getting flashcards", e)
        Result.failure(e)
    }

    override suspend fun generateFlashCard(
        count: Int,
        input: String
    ): List<FlashCardDetailResponse> {
        return try {
            val systemPrompt = PromptUtils.getPrompt(input, count)

            val response = aiChatDataSource.simpleAiChat(
                input = input,
                systemPrompt = systemPrompt
            ) ?: return emptyList()

            parseFlashCards(response)

        } catch (e: Exception) {
            Log.e(TAG, "Error generating flashcard: ${e.message}", e)
            emptyList()
        }
    }

    private fun parseFlashCards(response: String): List<FlashCardDetailResponse> {
        val flashCards = mutableListOf<FlashCardDetailResponse>()
        val lines = response.lines()

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()

            // Tìm dòng bắt đầu bằng FRONT:
            if (line.startsWith("FRONT:")) {
                val front = line.removePrefix("FRONT:").trim()

                // Tìm dòng BACK: tiếp theo
                var back = ""
                for (j in (i + 1) until lines.size) {
                    val nextLine = lines[j].trim()
                    if (nextLine.startsWith("BACK:")) {
                        back = nextLine.removePrefix("BACK:").trim()
                        i = j
                        break
                    }
                }

                if (front.isNotBlank() && back.isNotBlank()) {
                    flashCards.add(
                        FlashCardDetailResponse(
                            front = front,
                            back = back
                        )
                    )
                }
            }
            i++
        }

        Log.d(TAG, "Parsed ${flashCards.size} flashcards from AI response")
        return flashCards
    }

    private fun FlashCard.toMap(): Map<String, Any> = mapOf(
        "id" to id.toString(),
        "categoryId" to categoryId.toString(),
        "front" to front,
        "back" to back,
        "created_at" to created_at
    )
}