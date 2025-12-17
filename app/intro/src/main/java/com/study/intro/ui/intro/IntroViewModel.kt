package com.study.intro.ui.intro

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.osprey.data.common.datasource.AppSharePrefs
import com.study.common.navigation.Screen
import com.study.intro.model.IntroData
import com.study.core.base.viewmodel.BaseUiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    val app: Application,
    val appSharePrefs: AppSharePrefs
) : BaseUiStateViewModel<IntroUiState, IntroUiEvent, IntroUiAction>(app) {

    val intros = MutableLiveData<ArrayList<IntroData>>(IntroData.getDefault(applicationContext()))


    override fun initialState(): IntroUiState = IntroUiState()

    override fun onCreate() {
        super.onCreate()
    }

    override fun handleAction(action: IntroUiAction) {
        when (action) {
            is IntroUiAction.OnFinishIntro -> onFinishIntro()
            is IntroUiAction.OnPageChanged -> onPageChanged(action.position)
            IntroUiAction.NavigateIntro -> navigatetoIntro()
        }
    }

    fun onFinishIntro() {
        viewModelScope.launch(Dispatchers.IO) {
            appSharePrefs.isFirstOpen = false
            withContext(Dispatchers.Main) {
                sendEvent(IntroUiEvent.NavigateToMain)
            }
        }
    }

    fun onPageChanged(position: Int) {
        updateState {
            it.copy(currentPage = position)
        }
    }

    private fun navigatetoIntro() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(IntroUiEvent.NavigateEducation((Screen.Intro)))
        }
    }
}