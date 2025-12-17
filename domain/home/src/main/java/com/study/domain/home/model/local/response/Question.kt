package com.study.domain.home.model.local.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Question(
    val content: String,
    val options: List<String>,
    val answer: String,
    val explain: String
) : Parcelable