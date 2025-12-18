package com.study.home.ui.home.profile.editprofile

import com.study.domain.user.model.User

sealed class EditProfileUiAction {
    object Back : EditProfileUiAction()
    data class SaveProfile(
        val user: User
    ) : EditProfileUiAction()
}