package com.study.home.ui.home.profile

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.user.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    app: Application,
    private val singoutUseCase: SignOutUseCase,
    ) : BaseUiStateViewModel<ProfileScreenUiState, ProfileScreenUiEvent, ProfileScreenUiAction>(app) {
    override fun initialState(): ProfileScreenUiState = ProfileScreenUiState()


    override fun handleAction(action: ProfileScreenUiAction) {
        super.handleAction(action)
        when(action){
            ProfileScreenUiAction.SignOut -> handleSignOut()
        }

    }

    private fun handleSignOut(){
        viewModelScope.launch(Dispatchers.IO) {
            singoutUseCase()
            sendEvent(ProfileScreenUiEvent.NavigateSignOut)
        }
    }

}