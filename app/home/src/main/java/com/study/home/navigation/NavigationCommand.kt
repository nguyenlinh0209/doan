package com.study.home.navigation

sealed class NavigationCommand {
    data class NavigateTo(val screen: Screen) : NavigationCommand()
    object NavigateBack : NavigationCommand()
    data class NavigateToDeepLink(val uri: String) : NavigationCommand()
}