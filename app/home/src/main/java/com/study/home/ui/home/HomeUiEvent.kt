package com.study.home.ui.home

import com.study.home.navigation.Screen

sealed class HomeUiEvent {
    data class NavigateTo(val screen: Screen) : HomeUiEvent()
    object NavigateBack : HomeUiEvent()
    data class NavigateToDeepLink(val uri: String) : HomeUiEvent()
}