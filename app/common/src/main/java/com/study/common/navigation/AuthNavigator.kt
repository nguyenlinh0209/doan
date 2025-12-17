package com.study.common.navigation

import android.content.Context

interface AuthNavigator {
    fun showWelcome(context: Context)
    fun showSignIn(context: Context, isShowSignUp : Boolean)
}