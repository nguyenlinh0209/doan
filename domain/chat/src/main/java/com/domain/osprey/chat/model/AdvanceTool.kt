package com.domain.osprey.chat.model

import android.content.Context
import android.os.Parcelable
import com.study.core.data.model.Selectable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdvanceTool(
    var type: AdvanceToolType = AdvanceToolType.OTHER,
    var bgIcon: String? = null,
    var suggestionCategory: String? = null,
    var isMostUse: Boolean = false,
    var order: Int = 0,
    override var isSelected: Boolean = false
) : Parcelable, Selectable {
    fun getInitialQuestion(context: Context): String? {
        return null
    }

    fun getName(context: Context): String {
        return type.getName(context)
    }
}