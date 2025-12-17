package com.study.auth.navigation

import android.content.Context
import com.study.auth.model.Constants
import com.study.auth.ui.sigin.SignInActivity
import com.study.auth.ui.welcome.WelcomeActivity
import com.study.common.navigation.AuthNavigator
import com.study.core.extension.openActivity

class AuthNavigatorImpl : AuthNavigator {
    override fun showWelcome(
        context: Context
    ) {
        context.openActivity<WelcomeActivity>()
    }

    override fun showSignIn(context: Context, isShowSignUp: Boolean) {
        context.openActivity<SignInActivity>(
            Constants.Intents.IS_SHOW_SIGN_UP to isShowSignUp
        )
    }

}