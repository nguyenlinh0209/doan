package com.study.intro.model

import android.content.Context
import android.os.Parcelable
import com.study.intro.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class IntroData(
    var title: String,
    var content: String = "",
    var bgResId: Int,
) : Parcelable {
    companion object {
        fun getDefault(context: Context): ArrayList<IntroData> {
            return arrayListOf(
                IntroData(
                    title = "",
                    content = "",
                    bgResId = R.drawable.bg_intro_1,
                ),
                IntroData(
                    title = "",
                    content = "",
                    bgResId = R.drawable.bg_intro_1,
                ),
                IntroData(
                    title = "",
                    content = "",
                    bgResId = R.drawable.bg_intro_1,
                )
            )
        }
    }
}