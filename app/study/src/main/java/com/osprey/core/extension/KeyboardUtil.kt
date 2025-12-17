package com.osprey.common.util

import android.view.View
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Extension function để theo dõi sự thay đổi trạng thái hiển thị của bàn phím
 *
 * @return Flow<Boolean> - emit true khi bàn phím hiển thị, false khi ẩn
 *
 * Sử dụng:
 * ```
 * viewLifecycleOwner.lifecycleScope.launch {
 *     rootView.keyboardVisibilityChanges().collect { isVisible ->
 *         if (isVisible) {
 *             // Bàn phím đang hiển thị
 *         } else {
 *             // Bàn phím đã ẩn
 *         }
 *     }
 * }
 * ```
 */
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

fun View.getKeyboardHeight(): Int {
    val windowInsets = ViewCompat.getRootWindowInsets(this) ?: return 0
    val imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
    val navInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())

    return maxOf(0, imeInsets.bottom - navInsets.bottom)
}

/**
 * Extension function để kiểm tra bàn phím có đang hiển thị không
 *
 * @return Boolean - true nếu bàn phím đang hiển thị
 */
fun View.isKeyboardVisible(): Boolean {
    val windowInsets = ViewCompat.getRootWindowInsets(this) ?: return false
    val imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
    return imeInsets.bottom > 0
}