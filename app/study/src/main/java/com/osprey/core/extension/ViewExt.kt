package com.osprey.core.extension

import android.view.View

private var lastClickTime = 0L

fun View.debounceClick(delay: Long = 500, action: (View) -> Unit) {
    setOnClickListener { view ->
        val now = System.currentTimeMillis()
        if (now - lastClickTime >= delay) {
            lastClickTime = now
            action(view)
        }
    }
}



fun View.show(isEmpty: Boolean) {
    visibility = View.VISIBLE
}


fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone(isEmpty: Boolean) {
    visibility = View.GONE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.toggleVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}


fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}


fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.isGone(): Boolean = visibility == View.GONE