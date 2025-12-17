package com.study.intro.ui.splash

sealed class SplashUiEvent {
    object NavigateToIntro : SplashUiEvent()
    object NavigateToMain : SplashUiEvent()
}
