package com.study.home.ui.home.profile.editprofile

sealed class EditProfileUiEvent {
    object ProfileSaved : EditProfileUiEvent()
    object Back : EditProfileUiEvent()

}