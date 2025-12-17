package com.study.main.navigation

import android.content.Context
import com.study.main.ui.main.MainActivity
import com.study.common.navigation.MainNavigator
import com.study.core.extension.openActivity
import com.wodox.domain.main.model.Constants

class MainNavigatorImpl : MainNavigator {
    override fun showMain(context: Context, isFirstLaunch: Boolean) {
        context.openActivity<MainActivity>(
            Constants.Intents.IS_FIRST_LAUNCH to isFirstLaunch
        )
    }

}
