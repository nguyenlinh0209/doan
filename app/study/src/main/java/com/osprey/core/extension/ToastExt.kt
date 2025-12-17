package com.osprey.core.extension


import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.toast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}


fun Context.toast(
    @StringRes messageRes: Int,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, messageRes, duration).show()
}


fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun Context.longToast(@StringRes messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
}


fun Any.toast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}