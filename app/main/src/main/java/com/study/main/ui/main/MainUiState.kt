package com.osprey.main.ui.main

import com.study.main.ui.main.bottombar.BottomBarMenu
import java.util.UUID

data class MainUiState(
    val menus: List<BottomBarMenu> = arrayListOf(),
    val email: String? = null,
    val userId: UUID? = null,
    val showAddButton: Boolean = true,
    val showLogButton: Boolean = false,
    val showIconProfile: Boolean = false,
    )