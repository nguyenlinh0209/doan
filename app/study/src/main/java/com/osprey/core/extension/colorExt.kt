package com.osprey.core.extension

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.toColorInt


val String.color: Int
    @ColorInt
    get() = try {
        this.toColorInt()
    } catch (e: IllegalArgumentException) {
        Color.BLACK
    }


fun Int.toHexColor(includeAlpha: Boolean = true): String {
    return if (includeAlpha) {
        String.format("#%08X", this)
    } else {
        String.format("#%06X", 0xFFFFFF and this)
    }
}

fun Int.adjustBrightness(factor: Float): Int {
    val alpha = Color.alpha(this)
    val red = (Color.red(this) * factor).toInt().coerceIn(0, 255)
    val green = (Color.green(this) * factor).toInt().coerceIn(0, 255)
    val blue = (Color.blue(this) * factor).toInt().coerceIn(0, 255)
    return Color.argb(alpha, red, green, blue)
}


fun Int.withAlpha(alpha: Int): Int {
    return Color.argb(
        alpha.coerceIn(0, 255),
        Color.red(this),
        Color.green(this),
        Color.blue(this)
    )
}

fun Int.withAlphaFloat(alpha: Float): Int {
    return withAlpha((alpha * 255).toInt())
}


fun Int.lighten(percentage: Float = 0.2f): Int {
    return adjustBrightness(1f + percentage)
}


fun Int.darken(percentage: Float = 0.2f): Int {
    return adjustBrightness(1f - percentage)
}

val Int.isDark: Boolean
    get() {
        val darkness = 1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
        return darkness >= 0.5
    }


val Int.contrastColor: Int
    get() = if (isDark) Color.WHITE else Color.BLACK