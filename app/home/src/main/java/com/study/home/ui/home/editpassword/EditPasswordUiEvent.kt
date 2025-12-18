package com.study.home.ui.home.editpassword

sealed class EditPasswordUiEvent {
    object UpdateSuccess : EditPasswordUiEvent()
    data class UpdateFailed(val message: String) : EditPasswordUiEvent()

    object Close :  EditPasswordUiEvent()
}