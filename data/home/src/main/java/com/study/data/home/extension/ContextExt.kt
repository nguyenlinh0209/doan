package com.study.data.home.extension

import android.content.Context
import com.study.core.app.AbstractApplication
import com.osprey.data.common.datasource.AppSharePrefs
import com.osprey.data.common.datasource.AppSharePrefsImpl


val Context.appSharePrefs: AppSharePrefs
    get() {
        val app = applicationContext as AbstractApplication
        return AppSharePrefsImpl(
            app.sharePrefs
        )
    }

val Context.isPremium
    get() = (applicationContext as AbstractApplication).isPremium
