package com.study.auth.ui.sigin

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.study.auth.model.Constants
import com.study.common.navigation.MainNavigator
import com.study.core.base.activity.BaseActivity
import com.study.core.extension.debounceClick
import com.study.core.extension.gone
import com.study.core.extension.launchWhenStarted
import com.study.core.extension.show
import com.study.core.extension.toast
import com.wodox.auth.R
import com.wodox.auth.databinding.ActivtySignInOutLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivtySignInOutLayoutBinding, SignUpViewModel>(
    SignUpViewModel::class
) {
    @Inject
    lateinit var mainNavigator: MainNavigator
    override fun layoutId(): Int = R.layout.activty_sign_in_out_layout

    private val isShowSignUp: Boolean by lazy {
        intent.getBooleanExtra(Constants.Intents.IS_SHOW_SIGN_UP, false)
    }

    override fun initialize() {
        setupUI()
        setupAction()
        observer()
    }

    private fun setupUI() {
        if (isShowSignUp) {
            binding.etFullName.show()
            binding.btnSignIn.text = getString(com.study.resources.R.string.sign_up)
            binding.tvSignInTitle.text = getString(com.study.resources.R.string.sign_up)
        } else {
            binding.etFullName.gone()
            binding.btnSignIn.text = getString(com.study.resources.R.string.sign_in)
        }
    }

    private fun setupAction() {
        binding.ivBack.debounceClick {
            finish()
        }
        binding.btnSignIn.isEnabled = false
        binding.btnSignIn.alpha = 0.5f

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                val isEnabled = email.isNotEmpty() && password.isNotEmpty()
                binding.btnSignIn.isEnabled = isEnabled
                binding.btnSignIn.alpha = if (isEnabled) 1f else 0.5f
            }
        }
        binding.etEmail.addTextChangedListener(watcher)
        binding.etPassword.addTextChangedListener(watcher)
        binding.btnSignIn.debounceClick {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val fullName = binding.etFullName.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignInActivity, "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@debounceClick
            }

            if (isShowSignUp) {
                viewModel.dispatch(SignUpUiAction.SignUp(email, password, fullName))
            } else {
                viewModel.dispatch(SignUpUiAction.SignIn(email, password))
            }
        }
    }

    private fun observer() {
        launchWhenStarted {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is SignUpUiEvent.Loading -> {
                        showLoading(event.loading)
                    }

                    is SignUpUiEvent.Success -> {
                        showLoading(false)
                        Toast.makeText(this@SignInActivity, event.message, Toast.LENGTH_SHORT)
                            .show()
                        mainNavigator.showMain(this@SignInActivity, true)
                    }

                    is SignUpUiEvent.Error -> {
                        showLoading(false)
                        toast("Inccorect email or password.Please try again")
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.visibility =
            if (isLoading) View.VISIBLE else View.GONE
        binding.llInput.visibility =
            if (!isLoading) View.VISIBLE else View.GONE
    }
}