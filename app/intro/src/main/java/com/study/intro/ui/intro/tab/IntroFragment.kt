package com.study.intro.ui.intro.tab

import android.os.Bundle
import com.bumptech.glide.Glide
import com.study.intro.databinding.FragmentIntroBinding
import com.study.intro.BR
import com.study.intro.R
import com.study.intro.model.IntroData
import dagger.hilt.android.AndroidEntryPoint
import com.study.intro.ui.intro.IntroViewModel
import com.study.core.base.fragment.BaseFragment
import com.study.core.extension.parcelable

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>(
    IntroViewModel::class
) {

    private val intro by lazy {
        arguments?.parcelable<IntroData>(KEY_INTRO)
    }

    override fun layoutId(): Int = R.layout.fragment_intro

    override fun initialize() {
        binding.setVariable(BR.intro, intro)
        intro?.bgResId?.let { resId ->
            Glide.with(requireContext())
                .load(resId)
                .into(binding.ivImage)
        }
    }

    companion object {
        private const val KEY_INTRO = "key_intro"

        @JvmStatic
        fun newInstance(intro: IntroData?) = IntroFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_INTRO, intro)
            }
        }
    }
}
