package com.study.intro.ui.splash


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import com.study.core.theme.AppTheme
import com.study.common.navigation.MainNavigator
import com.study.common.navigation.AuthNavigator
import com.study.intro.ui.intro.IntroActivity
import com.osprey.data.common.datasource.AppSharePrefs
import com.study.intro.ui.intro.IntroComposeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashComposeActivity : ComponentActivity() {

    @Inject
    lateinit var mainNavigator: MainNavigator
    private lateinit var viewModel: SlashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SlashViewModel::class.java]

        setContent {
            AppTheme {
                SplashScreenWithLogic(
                    viewModel = viewModel,
                    onNavigateToIntro = {
                        startActivity(
                            android.content.Intent(
                                this,
                                IntroComposeActivity::class.java
                            )
                        )
                        finish()
                    },
                    onNavigateToMain = {
                        mainNavigator.showMain(this, false)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SplashScreenWithLogic(
    viewModel: SlashViewModel,
    onNavigateToIntro: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                SplashUiEvent.NavigateToIntro -> onNavigateToIntro()
                SplashUiEvent.NavigateToMain -> onNavigateToMain()
            }
        }
    }

    SlashScreen(
        uiState = uiState,
        onAction = { action ->
            viewModel.dispatch(action)
        }
    )
}
