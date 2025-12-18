package com.study.home.ui.home.editpassword

sealed class EditPasswordUiAction {
    data class UpdatePassword(
        val currentPassword: String,
        val newPassword: String,
        val confirmPassword: String
    ) : EditPasswordUiAction()

    object Close : EditPasswordUiAction()
}