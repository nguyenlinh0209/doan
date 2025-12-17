package com.study.domain.home.model.local.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlashCardDetailResponse(
    var front: String,
    var back: String
) : Parcelable