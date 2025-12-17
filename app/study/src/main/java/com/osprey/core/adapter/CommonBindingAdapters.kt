package com.osprey.core.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osprey.core.viewmodel.ui.TMVVMAdapter

object CommonBindingAdapters {
    @BindingAdapter("android:visibility", "invisibleType", requireAll = false)
    @JvmStatic
    fun setVisibilityWithType(view: View, isVisible: Boolean, invisibleType: String?) {
        view.visibility = when {
            isVisible -> View.VISIBLE
            invisibleType?.lowercase() == "invisible" -> View.INVISIBLE
            else -> View.GONE
        }
    }

    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibilityInt(view: View, visibility: Int?) {
        view.visibility = visibility ?: View.GONE
    }

    @BindingAdapter("android:selected")
    @JvmStatic
    fun setSelected(view: View, isSelected: Boolean) {
        view.isSelected = isSelected
    }

    @BindingAdapter("android:enabled")
    @JvmStatic
    fun setEnabled(view: View, isEnabled: Boolean) {
        view.isEnabled = isEnabled
    }

    @BindingAdapter("android:alpha")
    @JvmStatic
    fun setAlpha(view: View, alpha: Float) {
        view.alpha = alpha
    }

    @BindingAdapter("android:background")
    @JvmStatic
    fun setBackground(view: View, drawableId: Int?) {
        if (drawableId != null && drawableId != 0) {
            view.setBackgroundResource(drawableId)
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun <T> setRecyclerViewItems(recyclerView: RecyclerView, items: List<T>?) {
        @Suppress("UNCHECKED_CAST")
        val adapter = recyclerView.adapter as? TMVVMAdapter<T>
        adapter?.submitList(items ?: emptyList())
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageResource(view: ImageView, resId: Int?) {
        resId?.let { view.setImageResource(it) }
    }

    @BindingAdapter("iconRes", "tintSelected", requireAll = true)
    @JvmStatic
    fun setIconWithTint(view: ImageView, iconRes: Int, isSelected: Boolean) {
        view.setImageResource(iconRes)
        val color = if (isSelected) {
            ContextCompat.getColor(view.context, com.osprey.core.R.color.colorPrimary)
        } else {
            ContextCompat.getColor(view.context, com.osprey.core.R.color.black)
        }

        view.setColorFilter(color)
    }


    @BindingAdapter("isVisible")
    @JvmStatic
    fun setIsVisible(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("isGone")
    @JvmStatic
    fun setIsGone(view: View, gone: Boolean?) {
        view.visibility = if (gone == true) View.GONE else View.VISIBLE
    }

    @BindingAdapter("isVisibleOrGone")
    @JvmStatic
    fun setIsVisibleOrGone(view: View, isVisible: Boolean?) {
        view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
    }

}
