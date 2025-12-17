package com.osprey.main.ui.main

import com.study.main.ui.main.bottombar.BottomBarMenu.BottomBarMenuType

sealed class MainUiAction {
    data class ChangeTab(val type: BottomBarMenuType) : MainUiAction()
}