package com.study.home.ui.home


sealed class HomeUiAction {
    object LoadData : HomeUiAction()
    data class FeatureClicked(val feature: String) : HomeUiAction()
    object Retry : HomeUiAction()
}

