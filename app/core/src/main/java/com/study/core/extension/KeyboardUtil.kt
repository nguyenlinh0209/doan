package com.study.core.extension

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@RequiresApi(Build.VERSION_CODES.R)
fun View.keyboardVisibilityChanges(): Flow<Boolean> = callbackFlow {
    val listener = View.OnApplyWindowInsetsListener { _, insets ->
        val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
        val isKeyboardVisible = imeInsets.bottom > 0
        trySend(isKeyboardVisible)
        insets
    }

    ViewCompat.setOnApplyWindowInsetsListener(this@keyboardVisibilityChanges,
        listener as OnApplyWindowInsetsListener?
    )

    val initialInsets = ViewCompat.getRootWindowInsets(this@keyboardVisibilityChanges)
    if (initialInsets != null) {
        val imeInsets = initialInsets.getInsets(WindowInsetsCompat.Type.ime())
        trySend(imeInsets.bottom > 0)
    } else {
        trySend(false)
    }

    awaitClose {
        ViewCompat.setOnApplyWindowInsetsListener(this@keyboardVisibilityChanges, null)
    }
}.distinctUntilChanged()

@RequiresApi(Build.VERSION_CODES.R)
fun View.getKeyboardHeight(): Int {
    val windowInsets = ViewCompat.getRootWindowInsets(this) ?: return 0
    val imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
    val navInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
    return maxOf(0, imeInsets.bottom - navInsets.bottom)
}

@RequiresApi(Build.VERSION_CODES.R)
fun View.isKeyboardVisible(): Boolean {
    val windowInsets = ViewCompat.getRootWindowInsets(this) ?: return false
    val imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
    return imeInsets.bottom > 0
}