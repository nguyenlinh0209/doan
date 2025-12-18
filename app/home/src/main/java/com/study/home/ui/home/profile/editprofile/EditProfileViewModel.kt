package com.study.home.ui.home.profile.editprofile

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.user.model.User
import com.study.domain.user.usecase.GetCurrentUserUseCase
import com.study.domain.user.usecase.UpdateUserUseCase
import com.study.home.ui.home.profile.ProfileScreenUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    app: Application,
    private val getUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseUiStateViewModel<EditProfileUiState, EditProfileUiEvent, EditProfileUiAction>(app) {

    override fun initialState(): EditProfileUiState = EditProfileUiState()

    init {
        loadUser()
    }

    override fun handleAction(action: EditProfileUiAction) {
        when (action) {
            EditProfileUiAction.Back -> {
                sendEvent(EditProfileUiEvent.Back)
            }

            is EditProfileUiAction.SaveProfile -> {
                saveProfile(action.user)
            }
        }
    }


    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUseCase()
            updateState { it.copy(user = user) }
        }
    }

    private fun saveProfile(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserUseCase(user)
            sendEvent(EditProfileUiEvent.ProfileSaved)
        }
    }
}


