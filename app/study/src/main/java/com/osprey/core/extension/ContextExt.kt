package com.osprey.core.extension

import android.content.Context
import java.io.File
import android.util.Size
import android.view.WindowManager
import android.os.Build
import android.graphics.Point
val Context.imageDir: File
    get() {
        val dir = File(filesDir, "images")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

fun Context.screenSize(): Size {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val wm = getSystemService(WindowManager::class.java)
        val metrics = wm.currentWindowMetrics
        val bounds = metrics.bounds
        Size(bounds.width(), bounds.height())
    } else {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getRealSize(point)
        Size(point.x, point.y)
    }
}