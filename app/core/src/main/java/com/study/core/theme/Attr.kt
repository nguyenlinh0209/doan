package com.study.core.theme


import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape

/**
 * Replaces attrs.xml with Kotlin classes
 * Type-safe alternative to XML styleable attributes
 */

// ============ OUTLINE ATTRIBUTES ============
@Immutable
data class OutlineAttrs(
    val outlineColor: Color = Color.Black,
    val outlineWidth: Float = 1f
) {
    companion object {
        fun default() = OutlineAttrs()
    }
}

// ============ SETTING ITEM ATTRIBUTES ============
@Immutable
data class SettingItemAttrs(
    val settingTitle: String = "",
    val settingIcon: Int? = null,  // Drawable resource ID
    val settingToggleEnabled: Boolean = false,
    val settingDetail: String = "",
    val settingArrowEnabled: Boolean = true,
    val settingShowLine: Boolean = true,
    val settingTitleAllCap: Boolean = false,
    val settingShowOriginIcon: Boolean = false
) {
    companion object {
        fun default() = SettingItemAttrs()

        // Builder pattern for complex configurations
        fun builder() = Builder()
    }

    class Builder {
        private var title: String = ""
        private var icon: Int? = null
        private var toggleEnabled: Boolean = false
        private var detail: String = ""
        private var arrowEnabled: Boolean = true
        private var showLine: Boolean = true
        private var titleAllCap: Boolean = false
        private var showOriginIcon: Boolean = false

        fun title(value: String) = apply { title = value }
        fun icon(value: Int) = apply { icon = value }
        fun toggleEnabled(value: Boolean) = apply { toggleEnabled = value }
        fun detail(value: String) = apply { detail = value }
        fun arrowEnabled(value: Boolean) = apply { arrowEnabled = value }
        fun showLine(value: Boolean) = apply { showLine = value }
        fun titleAllCap(value: Boolean) = apply { titleAllCap = value }
        fun showOriginIcon(value: Boolean) = apply { showOriginIcon = value }

        fun build() = SettingItemAttrs(
            settingTitle = title,
            settingIcon = icon,
            settingToggleEnabled = toggleEnabled,
            settingDetail = detail,
            settingArrowEnabled = arrowEnabled,
            settingShowLine = showLine,
            settingTitleAllCap = titleAllCap,
            settingShowOriginIcon = showOriginIcon
        )
    }
}

// ============ SHADOW VIEW ATTRIBUTES ============
@Immutable
data class ShadowViewAttrs(
    val svMask: Int? = null,        // Drawable resource ID
    val svColor: Color = Color.Black,
    val svDx: Dp = 1.dp,             // Horizontal displacement
    val svDy: Dp = 2.dp              // Vertical displacement
) {
    companion object {
        fun default() = ShadowViewAttrs()

        // Preset shadow styles
        fun circleDefault() = ShadowViewAttrs(
            svColor = Color.Black.copy(alpha = 0.2f),
            svDx = 1.dp,
            svDy = 2.dp
        )

        fun softShadow() = ShadowViewAttrs(
            svColor = Color.Black.copy(alpha = 0.1f),
            svDx = 0.dp,
            svDy = 4.dp
        )

        fun hardShadow() = ShadowViewAttrs(
            svColor = Color.Black.copy(alpha = 0.3f),
            svDx = 2.dp,
            svDy = 4.dp
        )
    }
}

// ============ COLOR THEME ATTRIBUTES ============
@Immutable
data class ColorThemeAttrs(
    val thirdBackgroundColor: Color = Color(0xFFF5F5F5),
    val disableColor: Color = Color(0xFFD1D1D6),
    val gradientColors: List<Color> = listOf(
        Color(0xFFDC96FF),
        Color(0xFFFF74CF)
    ),
    val secondFontBold: Boolean = false
) {
    companion object {
        fun default() = ColorThemeAttrs()

        // Light theme
        fun lightTheme() = ColorThemeAttrs(
            thirdBackgroundColor = Color(0xFFF5F5F5),
            disableColor = Color(0xFFD1D1D6),
            gradientColors = listOf(
                Color(0xFFDC96FF),
                Color(0xFFFF74CF)
            ),
            secondFontBold = false
        )

        // Dark theme
        fun darkTheme() = ColorThemeAttrs(
            thirdBackgroundColor = Color(0xFF1E1E1E),
            disableColor = Color(0xFF434343),
            gradientColors = listOf(
                Color(0xFFBB86FC),
                Color(0xFF6C63FF)
            ),
            secondFontBold = true
        )
    }
}

// ============ SHAPE & STYLE ATTRIBUTES ============
@Immutable
data class ShapeStyleAttrs(
    val cornerRadius: Dp = 8.dp,
    val shape: Shape = RoundedCornerShape(8.dp),
    val clipToOutline: Boolean = true
) {
    companion object {
        fun default() = ShapeStyleAttrs()

        fun roundedSmall() = ShapeStyleAttrs(
            cornerRadius = 4.dp,
            shape = RoundedCornerShape(4.dp),
            clipToOutline = true
        )

        fun roundedMedium() = ShapeStyleAttrs(
            cornerRadius = 8.dp,
            shape = RoundedCornerShape(8.dp),
            clipToOutline = true
        )

        fun roundedLarge() = ShapeStyleAttrs(
            cornerRadius = 12.dp,
            shape = RoundedCornerShape(12.dp),
            clipToOutline = true
        )

        fun circle() = ShapeStyleAttrs(
            cornerRadius = 50.dp,
            shape = RoundedCornerShape(50.dp),
            clipToOutline = true
        )
    }
}

// ============ COMBINED THEME CONFIGURATION ============
@Immutable
data class AppThemeConfig(
    val colorTheme: ColorThemeAttrs = ColorThemeAttrs.default(),
    val shadowView: ShadowViewAttrs = ShadowViewAttrs.circleDefault(),
    val outlineAttrs: OutlineAttrs = OutlineAttrs.default(),
    val shapeStyle: ShapeStyleAttrs = ShapeStyleAttrs.roundedMedium()
) {
    companion object {
        fun default() = AppThemeConfig()

        fun lightMode() = AppThemeConfig(
            colorTheme = ColorThemeAttrs.lightTheme(),
            shadowView = ShadowViewAttrs.softShadow(),
            outlineAttrs = OutlineAttrs(
                outlineColor = Color(0xFFE0E0E0),
                outlineWidth = 1f
            ),
            shapeStyle = ShapeStyleAttrs.roundedMedium()
        )

        fun darkMode() = AppThemeConfig(
            colorTheme = ColorThemeAttrs.darkTheme(),
            shadowView = ShadowViewAttrs.hardShadow(),
            outlineAttrs = OutlineAttrs(
                outlineColor = Color(0xFF2C2C2C),
                outlineWidth = 1f
            ),
            shapeStyle = ShapeStyleAttrs.roundedMedium()
        )
    }
}
