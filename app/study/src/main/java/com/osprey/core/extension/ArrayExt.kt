package com.osprey.core.extension

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}