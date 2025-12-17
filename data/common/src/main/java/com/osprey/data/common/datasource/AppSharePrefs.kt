package com.osprey.data.common.datasource

import com.study.core.data.model.SharePrefs
import java.util.UUID


interface AppSharePrefs: SharePrefs {
    var hadASuccessfulKeyboardInstallation: Boolean
    var isChatSuggestion: Boolean
    var isEnableHomeSendAnimation: Boolean

    var authToken: String?
    var userId: UUID?

    fun clearAuthData()

}