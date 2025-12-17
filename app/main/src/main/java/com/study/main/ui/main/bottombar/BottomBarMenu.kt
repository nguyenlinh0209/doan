package com.study.main.ui.main.bottombar

import android.content.Context
import com.osprey.main.ui.main.PageType
import com.study.resources.R
import com.study.core.data.model.Selectable

data class BottomBarMenu(
    var type: BottomBarMenuType,
    var title: String,
    var icon: Int,
    var selectedIcon: Int,
    override var isSelected: Boolean = false
) : Selectable {
    enum class BottomBarMenuType(val page: PageType, val index: Int) {
        HOME(PageType.HOME, 0),
        SCENE(PageType.SCENE, 1),
        MALL(PageType.MALL, 2),
        PROFILE(PageType.PROFILE, 3);
    }

    companion object {
        fun getDefaults(context: Context): List<BottomBarMenu> {
            return arrayListOf(
                BottomBarMenu(
                    BottomBarMenuType.HOME,
                    context.getString(R.string.home),
                    R.drawable.ic_home_normal,
                    R.drawable.ic_home
                ),
                BottomBarMenu(
                    BottomBarMenuType.SCENE,
                    context.getString(R.string.scene),
                    R.drawable.ic_scene,
                    R.drawable.ic_scene
                ),
                BottomBarMenu(
                    BottomBarMenuType.MALL,
                    context.getString(R.string.mall),
                    R.drawable.ic_mall,
                    R.drawable.ic_mall
                ),
                BottomBarMenu(
                    BottomBarMenuType.PROFILE,
                    context.getString(R.string.profile),
                    R.drawable.ic_user_profile,
                    R.drawable.ic_user_profile
                ),
            )
        }
    }
}