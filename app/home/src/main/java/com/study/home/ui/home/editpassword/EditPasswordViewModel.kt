package com.study.home.ui.home.editpassword

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.user.usecase.UpdatePasswordRequest
import com.study.domain.user.usecase.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    app: Application,
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : BaseUiStateViewModel<
        EditPasswordUiState,
        EditPasswordUiEvent,
        EditPasswordUiAction
        >(app) {

    override fun initialState() = EditPasswordUiState()

    override fun handleAction(action: EditPasswordUiAction) {
        when (action) {
            is EditPasswordUiAction.UpdatePassword -> {
                handleUpdatePassword(
                    action.currentPassword,
                    action.newPassword,
                    action.confirmPassword
                )
            }

            EditPasswordUiAction.Close -> {
                sendEvent(EditPasswordUiEvent.Close)
            }
        }
    }

    private fun handleUpdatePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        val error = validatePasswords(currentPassword, newPassword, confirmPassword)
        if (error != null) {
            updateState { it.copy(error = error) }
            return
        }

        updateState { it.copy(isLoading = true, error = null) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                updatePasswordUseCase(
                    UpdatePasswordRequest(
                        currentPassword = currentPassword,
                        newPassword = newPassword
                    )
                )
                updateState { it.copy(isLoading = false) }
                sendEvent(EditPasswordUiEvent.UpdateSuccess)

            } catch (e: Exception) {
                val msg = e.message ?: "Cập nhật mật khẩu thất bại"
                updateState { it.copy(isLoading = false, error = msg) }
                sendEvent(EditPasswordUiEvent.UpdateFailed(msg))
            }
        }
    }

    private fun validatePasswords(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): String? = when {
        currentPassword.isBlank() -> "Vui lòng nhập mật khẩu hiện tại"
        newPassword.isBlank() -> "Vui lòng nhập mật khẩu mới"
        confirmPassword.isBlank() -> "Vui lòng xác nhận mật khẩu mới"
        newPassword.length < 6 -> "Mật khẩu mới phải có ít nhất 6 ký tự"
        newPassword != confirmPassword -> "Mật khẩu xác nhận không trùng khớp"
        currentPassword == newPassword -> "Mật khẩu mới phải khác mật khẩu cũ"
        else -> null
    }
}
