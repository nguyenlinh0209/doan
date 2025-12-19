package com.study.home.ui.home.profile

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.user.usecase.GetCurrentUserUseCase
import com.study.domain.user.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    app: Application,
    private val singoutUseCase: SignOutUseCase,
    private val getUserUseCase: GetCurrentUserUseCase,
) : BaseUiStateViewModel<ProfileScreenUiState, ProfileScreenUiEvent, ProfileScreenUiAction>(app) {
    override fun initialState(): ProfileScreenUiState = ProfileScreenUiState()

   init {
        loadUser()
    }

    override fun handleAction(action: ProfileScreenUiAction) {
        super.handleAction(action)
        when (action) {
            ProfileScreenUiAction.SignOut -> handleSignOut()
            ProfileScreenUiAction.EditProfile -> navigateEdit()
            ProfileScreenUiAction.ChangePassword -> navigateChangePassword()
            ProfileScreenUiAction.OnResume -> loadUser()
        }
    }

    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUseCase()
            updateState { it.copy(user = user) }
        }
    }

    private fun handleSignOut() {
        viewModelScope.launch(Dispatchers.IO) {
            singoutUseCase()
            sendEvent(ProfileScreenUiEvent.NavigateSignOut)
        }
    }

    private fun navigateEdit() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(ProfileScreenUiEvent.NavigateEdit)
        }
    }

    private fun navigateChangePassword(){
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(ProfileScreenUiEvent.NavigateChangePassword)
        }
    }
}