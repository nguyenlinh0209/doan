package com.osprey.main.ui.main

enum class PageType(val index: Int, val group: Group) {
    HOME(0, Group.BOTTOM),
    SCENE(1, Group.BOTTOM),
    MALL(2, Group.BOTTOM),
    PROFILE(3, Group.BOTTOM);

    enum class Group { BOTTOM }

    companion object {
        fun fromIndex(index: Int): PageType? {
            return entries.find { it.index == index }
        }
    }
}
