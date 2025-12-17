package com.study.intro.ui.intro

sealed class IntroUiAction {
    object OnFinishIntro: IntroUiAction()
   data class OnPageChanged(val position:Int): IntroUiAction()

    object NavigateIntro : IntroUiAction()
}
