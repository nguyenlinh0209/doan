package com.study.home.model

import com.study.home.navigation.Screen

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: String
)