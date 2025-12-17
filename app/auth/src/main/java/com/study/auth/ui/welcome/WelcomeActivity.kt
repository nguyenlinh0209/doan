package com.study.auth.ui.welcome

import com.wodox.auth.R
import com.wodox.auth.databinding.ActivityWelcomeLayoutBinding
import com.study.auth.ui.sigin.SignUpViewModel
import com.study.common.navigation.AuthNavigator
import com.study.core.base.activity.BaseActivity
import com.study.core.extension.debounceClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeLayoutBinding, SignUpViewModel>(
    SignUpViewModel::class
) {

    @Inject
    lateinit var authNavigator: AuthNavigator
    override fun initialize() {
        setupUI()
        setupAction()
    }

    private fun setupUI() {

    }

    private fun setupAction() {
        binding.apply {
            btnSignIn.debounceClick {
                authNavigator.showSignIn(this@WelcomeActivity, false)
            }
            btnSignUp.debounceClick {
                authNavigator.showSignIn(this@WelcomeActivity, true)

            }
        }
    }

    override fun layoutId(): Int = R.layout.activity_welcome_layout

}