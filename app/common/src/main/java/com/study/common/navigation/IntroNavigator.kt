package com.study.common.navigation

import android.content.Context

interface IntroNavigator {
    fun openSplash(context: Context, isMoveToForeground: Boolean = false)
}