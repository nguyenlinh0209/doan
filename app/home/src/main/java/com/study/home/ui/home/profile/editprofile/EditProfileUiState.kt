package com.study.home.ui.home.profile.editprofile

import com.study.domain.user.model.User

data class EditProfileUiState (
    val user : User? = null,
    val isLoading: Boolean = false

)