package com.study.home.ui.home.profile

sealed class ProfileScreenUiEvent {
    object NavigateSignOut : ProfileScreenUiEvent()
    object NavigateEdit : ProfileScreenUiEvent()
    object NavigateChangePassword: ProfileScreenUiEvent()

}