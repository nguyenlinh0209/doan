package com.osprey.core.extension

import android.content.Context
import java.io.IOException

object FileUtils {

    fun loadJsonFromAsset(context: Context, filePath: String): String {
        return try {
            context.assets.open(filePath).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    fun loadFileFromAsset(context: Context, filePath: String): String {
        return loadJsonFromAsset(context, filePath)
    }

    fun fileExists(context: Context, filePath: String): Boolean {
        return try {
            context.assets.list(filePath.substringBeforeLast("/"))
                ?.contains(filePath.substringAfterLast("/")) ?: false
        } catch (e: Exception) {
            false
        }
    }
}
