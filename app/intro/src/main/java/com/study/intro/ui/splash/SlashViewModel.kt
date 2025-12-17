package com.study.intro.ui.splash

import android.app.Application
import com.osprey.data.common.datasource.AppSharePrefs
import com.study.intro.ui.intro.IntroUiAction
import com.study.intro.ui.intro.IntroUiEvent
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.intro.ui.intro.IntroUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlashViewModel @Inject constructor(
    val app: Application,
    val appSharePrefs: AppSharePrefs
) : BaseUiStateViewModel<
        SplashUiState,
        SplashUiEvent,
        SplashUiAction
        >(app) {

    override fun initialState(): SplashUiState = SplashUiState()

    override fun handleAction(action: SplashUiAction) {
        when (action) {
            SplashUiAction.CheckFirstOpen -> checkFirstOpen()
        }
    }

    private fun checkFirstOpen() {
        if (appSharePrefs.isFirstOpen) {
            sendEvent(SplashUiEvent.NavigateToIntro)
        } else {
            sendEvent(SplashUiEvent.NavigateToMain)
        }
    }
}
