package com.study.intro.ui.intro

import com.study.common.navigation.Screen

sealed class IntroUiEvent {
    object NavigateToMain : IntroUiEvent()

    data class  NavigateEducation(val screen: Screen) : IntroUiEvent()
}
