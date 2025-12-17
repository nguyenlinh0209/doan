package com.study.data.home.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.study.common.util.PromptUtils
import com.study.data.home.datasource.remote.datasource.AIChatDataSource
import com.study.domain.home.model.local.Quizz
import com.study.domain.home.model.local.response.Question
import com.study.domain.home.repository.QuizzRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val aiChatDataSource: AIChatDataSource,
) : QuizzRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val quizCollection = firestore.collection("quizzes")

    companion object {
        private const val TAG = "QuizRepo"
    }

    override suspend fun saveFlashCard(quizz: Quizz): Quizz {
        quizCollection
            .document(quizz.id)
            .set(quizz.toMap())
            .await()

        Log.d(TAG, "Quiz saved: ${quizz.id}")
        return quizz
    }

    override suspend fun generateQuiz(
        count: Int,
        input: String
    ): List<Question> {
        return try {
            val systemPrompt = PromptUtils.getQuizPrompt(input, count)

            val response = aiChatDataSource.simpleAiChat(
                input = input,
                systemPrompt = systemPrompt
            ) ?: return emptyList()

            parseQuiz(response)

        } catch (e: Exception) {
            Log.e(TAG, "Error generating quiz", e)
            emptyList()
        }
    }

    private fun parseQuiz(response: String): List<Question> {
        val questions = mutableListOf<Question>()
        val lines = response.lines()

        var content = ""
        val options = mutableListOf<String>()
        var answer = ""
        var explain = ""

        for (line in lines) {
            val trimmed = line.trim()

            when {
                trimmed.startsWith("CONTENT:") -> {
                    content = trimmed.removePrefix("CONTENT:").trim()
                }

                trimmed.startsWith("A:") ||
                        trimmed.startsWith("B:") ||
                        trimmed.startsWith("C:") ||
                        trimmed.startsWith("D:") -> {
                    options.add(trimmed.substring(2).trim())
                }

                trimmed.startsWith("ANSWER:") -> {
                    answer = trimmed.removePrefix("ANSWER:").trim()
                }

                trimmed.startsWith("EXPLAIN:") -> {
                    explain = trimmed.removePrefix("EXPLAIN:").trim()

                    if (
                        content.isNotBlank() &&
                        options.size == 4 &&
                        answer.isNotBlank()
                    ) {
                        questions.add(
                            Question(
                                content = content,
                                options = options.toList(),
                                answer = answer,
                                explain = explain
                            )
                        )
                    }

                    content = ""
                    options.clear()
                    answer = ""
                    explain = ""
                }
            }
        }

        Log.d(TAG, "Parsed ${questions.size} questions")
        return questions
    }

    private fun Quizz.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "topic" to topic,
        "difficulty" to difficulty.name,
        "questionCount" to questionCount
    )
}
