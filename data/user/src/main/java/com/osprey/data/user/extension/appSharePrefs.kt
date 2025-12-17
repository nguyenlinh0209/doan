package com.osprey.data.user.extension

import android.content.Context
import com.osprey.data.common.datasource.AppSharePrefs
import com.osprey.data.common.datasource.AppSharePrefsImpl
import com.study.core.app.AbstractApplication

val Context.appSharePrefs: AppSharePrefs
    get() {
        val app = applicationContext as AbstractApplication
        return AppSharePrefsImpl(
            app.sharePrefs
        )
    }


val Context.isPremium
    get() = (applicationContext as AbstractApplication).isPremium
