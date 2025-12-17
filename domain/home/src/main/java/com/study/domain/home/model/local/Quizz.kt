package com.study.domain.home.model.local

data class Quizz(
    val id: String,
    val topic: String,
    val difficulty: Difficulty,
    val questionCount: Int
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}