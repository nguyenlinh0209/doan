package com.study.common.navigation

import androidx.recyclerview.widget.RecyclerView
import com.study.core.data.model.Selectable
import com.study.core.ui.TMVVMAdapter


fun <T : Selectable> TMVVMAdapter<T>.reloadChangedItems(predicate: (T) -> Boolean) {

    val previousIndex = this.list.indexOfFirst { it.isSelected == true }
    val currentIndex = this.list.indexOfFirst { predicate(it) }

    if (previousIndex == currentIndex) return

    this.list.forEach { item ->
        item.isSelected = predicate(item)
    }

    if (previousIndex != -1) notifyItemChanged(previousIndex)

    if (currentIndex != -1) notifyItemChanged(currentIndex)
}

/**
 * Extension cho RecyclerView
 */
@Suppress("UNCHECKED_CAST")
fun <T : Selectable> RecyclerView.reloadChangedItems(predicate: (T) -> Boolean) {
    (this.adapter as? TMVVMAdapter<T>)?.reloadChangedItems(predicate)
}
