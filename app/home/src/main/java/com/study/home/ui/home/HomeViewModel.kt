package com.study.home.ui.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.core.base.viewmodel.EmptyUiEvent
import com.study.home.navigation.NavigationCommand
import com.study.home.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application
) : BaseUiStateViewModel<HomeUiState, HomeUiEvent, HomeUiAction>(app) {

    override fun initialState() = HomeUiState()

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.LoadData -> loadData()
            HomeUiAction.Retry -> loadData()
            is HomeUiAction.FeatureClicked -> navigate(action.feature)
        }
    }

    private fun navigate(feature: String) {
        viewModelScope.launch(Dispatchers.Main) {
            when (feature) {
                "Flash Card" -> {
                    sendEvent(HomeUiEvent.NavigateTo(Screen.CategoryFlashCard))
                }

                "Tạo Quiz" -> {
                    sendEvent(HomeUiEvent.NavigateTo(Screen.QuizShow))
                }
                "Gia sư AI" -> {
                    sendEvent(HomeUiEvent.NavigateTo(Screen.AICHAT))
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState {
                it.copy(
                    streak = 5,
                )
            }
        }
    }
}

