package com.osprey.data.common.extension

import android.content.Context
import com.study.core.app.AbstractApplication

val Context.isPremium
    get() = (applicationContext as AbstractApplication).isPremium
