package com.study.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.study.calendar.ui.CalendarScreenContainer
import com.study.camera.ui.CameraScreenContainer
import com.study.common.navigation.AuthNavigator
import com.study.home.ui.home.HomeScreenContainer
import com.study.home.ui.home.aichat.AIChatScreenContainer
import com.study.home.ui.home.editpassword.EditPasswordScreenContainer
import com.study.home.ui.home.flashcard.ScreenFlashCardContainer
import com.study.home.ui.home.flashcard.managementflashcard.FlashCardContainer
import com.study.home.ui.home.profile.ProfileScreenContainer
import com.study.home.ui.home.profile.editprofile.EditProfileContainer
import com.study.home.ui.home.quizz.QuizzContainer
import java.util.UUID

@Composable
fun AppNavigation(
    navController: NavHostController,
    authNavigator: AuthNavigator,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreenContainer(navController)
        }
        composable(Screen.Calendar.route) {
            CalendarScreenContainer(navController)
        }

        composable(Screen.Camera.route) {
            CameraScreenContainer(navController)
        }

        composable(Screen.CategoryFlashCard.route) {
            ScreenFlashCardContainer(navController)
        }
        composable(
            route = Screen.FlashCard.route,
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val categoryIdString =
                backStackEntry.arguments?.getString("categoryId")

            val categoryId = try {
                UUID.fromString(categoryIdString)
            } catch (e: Exception) {
                UUID.randomUUID()
            }

            FlashCardContainer(
                navController = navController,
                categoryId = categoryId
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreenContainer(navController, authNavigator)
        }
        composable(Screen.QuizShow.route) {
            QuizzContainer(navController)
        }
        composable(Screen.AICHAT.route) {
            AIChatScreenContainer(navController)
        }
        composable(Screen.EDITPROFILE.route) {
            EditProfileContainer(navController)
        }
        composable(Screen.EditPassword.route) {
            EditPasswordScreenContainer(navController)
        }
    }
}
