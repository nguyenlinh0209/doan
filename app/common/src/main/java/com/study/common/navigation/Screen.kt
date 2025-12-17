package com.study.common.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Calendar : Screen("calendar")
    object Camera : Screen("camera")
    object Target : Screen("target")
    object Profile : Screen("profile")

    object Intro : Screen("intro")

    object QuizDetail : Screen("quiz/{quizId}") {
        fun createRoute(quizId: String) = "quiz/$quizId"
    }


    companion object {
        fun fromRoute(route: String?): Screen? =
            when (route) {
                Home.route -> Home
                Calendar.route -> Calendar
                Camera.route -> Camera
                Target.route -> Target
                Profile.route -> Profile
                Intro.route -> Intro
                else -> null
            }
    }

}