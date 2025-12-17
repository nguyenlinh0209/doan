package com.study.home.navigation

import java.util.UUID

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Calendar : Screen("calendar")
    object Camera : Screen("camera")
    object Target : Screen("target")
    object Profile : Screen("profile")

    object CategoryFlashCard : Screen("category_flashcard")

    object AddFlashCard : Screen("add_flashcard")

    object Slash : Screen("slash")

    object QuizDetail : Screen("quiz/{quizId}") {
        fun createRoute(quizId: String) = "quiz/$quizId"
    }

    object FlashCard : Screen("flashcard/{categoryId}") {
        fun createRoute(categoryId: UUID) = "flashcard/$categoryId"
    }

    companion object {
        fun fromRoute(route: String?): Screen? =
            when {
                route == Home.route -> Home
                route == Calendar.route -> Calendar
                route == Camera.route -> Camera
                route == Profile.route -> Profile
                route?.startsWith("flashcard/") == true -> FlashCard
                route == CategoryFlashCard.route -> CategoryFlashCard
                route == AddFlashCard.route -> AddFlashCard
                else -> null
            }
    }
}