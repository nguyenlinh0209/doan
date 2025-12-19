package com.study.home.ui.home.profile

sealed class ProfileScreenUiAction {
    object SignOut : ProfileScreenUiAction()

    object EditProfile : ProfileScreenUiAction()
    object ChangePassword : ProfileScreenUiAction()
    object OnResume : ProfileScreenUiAction()
}
