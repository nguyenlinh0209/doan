package com.study.intro.navigation

import android.content.Context
import com.study.common.navigation.IntroNavigator
import com.study.intro.ui.splash.SplashActivity
import com.study.core.extension.openActivity

class IntroNavigatorImpl: IntroNavigator {
    override fun openSplash(context: Context, isMoveToForeground: Boolean) {
        return context.openActivity<SplashActivity>(
        )
    }
}