package com.study.intro.ui.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.study.core.theme.AppTheme
import com.study.common.navigation.MainNavigator
import com.study.common.navigation.AuthNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroComposeActivity : ComponentActivity() {

    @Inject
    lateinit var mainNavigator: MainNavigator

    @Inject
    lateinit var authNavigator: AuthNavigator

    private lateinit var viewModel: IntroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[IntroViewModel::class.java]

        setContent {
            AppTheme {
                IntroNavigation(
                    viewModel = viewModel,
                    onComplete = { level, grade ->
                        authNavigator.showWelcome(this)
                        finish()
                    },
                    onBack = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun IntroNavigation(
    viewModel: IntroViewModel,
    onComplete: (String, String) -> Unit,
    onBack: () -> Unit
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is IntroUiEvent.NavigateToMain -> {

                }

                is IntroUiEvent.NavigateEducation -> navController.navigate(event.screen.route)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "education_flow"
    ) {
        composable("education_flow") {
            EducationFlowScreen(
                onNavigateBack = onBack,
                onComplete = onComplete
            )
        }

    }
}