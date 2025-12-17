package com.study.main.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.study.common.navigation.AuthNavigator
import com.study.core.theme.AppTheme
import com.study.home.navigation.AppNavigation
import com.study.home.navigation.Screen
import com.study.home.ui.home.menu.BottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authNavigator: AuthNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val currentRoute =
                navController.currentBackStackEntryFlow
                    .collectAsState(initial = navController.currentBackStackEntry)
                    .value
                    ?.destination
                    ?.route

            val currentScreen =
                Screen.fromRoute(currentRoute) ?: Screen.Home

            AppTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            currentScreen = currentScreen,
                            onScreenSelected = { screen ->
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    AppNavigation(
                        navController = navController,
                        authNavigator = authNavigator,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}
