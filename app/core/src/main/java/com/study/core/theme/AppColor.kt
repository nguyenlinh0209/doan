package com.study.core.theme

import androidx.compose.ui.graphics.Color

/**
 * Centralized color values for the entire app
 * Replaces colors.xml with a Kotlin object for type safety
 */
object AppColor {

    // ============ CORE COLORS ============
    val Primary = Color(0xFF6200EE)
    val Secondary = Color(0xFF03DAC6)
    val Background = Color(0xFFFAFAFA)
    val Surface = Color.White


    // ============ DARK THEME COLORS ============
    val DarkPrimary = Color(0xFFBB86FC)
    val DarkSecondary = Color(0xFF03DAC6)
    val DarkBackground = Color(0xFF121212)
    val DarkSurface = Color(0xFF1E1E1E)
    val DarkOnPrimary = Color(0xFF000000)
    val DarkOnBackground = Color(0xFFFFFFFF)

    // ============ FEATURE BACKGROUNDS ============
    val AiTutorBg = Color(0xFFB3E5FC)
    val QuizBg = Color(0xFFC8E6C9)
    val FlashCardBg = Color(0xFFE1BEE7)
    val HistoryBg = Color(0xFFE0E0E0)

    // ============ BASE COLORS ============
    val white = Color(0xFFFFFFFF)
    val black = Color(0xFF000000)
    val transparent = Color(0x00000000)

    // ============ PRIMARY & ACCENT ============
    val colorPrimary = Color(0xFF2196F3)
    val colorAccent = Color(0xFFFF5722)
    val color_primary = Color(0xFF1976D2)

    // ============ SECONDARY COLORS ============
    val color6C63FF = Color(0xFF6C63FF)
    val purple_accent = Color(0xFFBB86FC)
    val purple_600 = Color(0xFF6650A4)
    val color952EFF = Color(0xFF952EFF)

    // ============ BLUE SHADES ============
    val color75C5FF = Color(0xFF75C5FF)
    val color65AEE2 = Color(0xFF65AEE2)
    val color9EDFFF = Color(0xFF9EDFFF)
    val color78D7FF = Color(0xFF78D7FF)
    val colorE3F2FD = Color(0xFFE3F2FD)
    val color6366F1 = Color(0xFF6366F1)
    val color1A6366F1 = Color(0xFF1A6366F1)
    val colorD1E7F7 = Color(0xFFD1E7F7)

    // ============ ORANGE SHADES ============
    val colorFF8400 = Color(0xFFFF8400)
    val colorF37E02 = Color(0xFFF37E02)
    val color3D2A00 = Color(0xFF3D2A00)
    val color7F5700 = Color(0xFF7F5700)
    val colorFFBD2F = Color(0xFFFFBD2F)
    val colorFFC340 = Color(0xFFFFC340)
    val colorFFC860 = Color(0xFFFFC860)
    val colorFFE29E = Color(0xFFFFE29E)
    val colorD6A024 = Color(0xFFD6A024)
    val colorFFD2AA = Color(0xFFFFD2AA)

    // ============ YELLOW ============
    val colorFFEC8D = Color(0xFFFFEC8D)

    // ============ GREEN ============
    val color75E073 = Color(0xFF75E073)
    val color75E772 = Color(0xFF75E772)
    val colorD5DFBD = Color(0xFFD5DFBD)

    // ============ ERROR / RED ============
    val error = Color(0xFFEF4444)
    val error_light = Color(0xFFFEE2E2)
    val colorDelete = Color(0xFFFF8686)
    val colorFF6284 = Color(0xFFFF6284)
    val priority_high = Color(0xFFF44336)

    // ============ GRAY ============
    val disableGrayColor = Color(0xFFD1D1D6)
    val colorE1E0E0 = Color(0xFFE1E0E0)
    val color2B2B2B = Color(0xFF2B2B2B)
    val colorCBCBCB = Color(0xFFCBCBCB)
    val grayC8C8C8 = Color(0xFFC8C8C8)
    val gray434343 = Color(0xFF434343)
    val gray505050 = Color(0xFF505050)
    val gray_text = Color(0xFF9E9E9E)
    val gray_icon = Color(0xFF757575)
    val light_gray = Color(0xFFE9ECEF)
    val color_light_gray = Color(0xFFEEEEEE)
    val gray1F1F1F = Color(0xFF1F1F1F)
    val colorD1D5DB = Color(0xFFD1D5DB)
    val colorD3D3D3 = Color(0xFFD3D3D3)
    val priority_low = Color(0xFF9E9E9E)
    val drag_handle_color = Color(0xFFC7C7CC)

    // ============ DARK UI ============
    val dark_background = Color(0xFF1E1E1E)
    val divider_color = Color(0xFF2C2C2C)

    // ============ TEXT ============
    val text_primary = Color(0xFF212121)
    val text_secondary = Color(0xFF757575)
    val text_hint = Color(0xFFBDBDBD)
    val color_text_secondary = Color(0xFF666666)
    val color_text_tertiary = Color(0xFF999999)
    val color1F2937 = Color(0xFF1F2937)

    // ============ ICON ============
    val icon_primary = Color(0xFF424242)
    val icon_secondary = Color(0xFF6B7280)

    // ============ BACKGROUND ============
    val background_primary = Color(0xFFFFFFFF)
    val background_secondary = Color(0xFFF5F5F5)
    val background_color = Color(0xFFF5F5F5)
    val colorF5F5F5 = Color(0xFFF5F5F5)
    val colorF3F4F6 = Color(0xFFF3F4F6)
    val colorFAFAFA = Color(0xFFFAFAFA)
    val surface_container = Color(0xFFF5F5F5)
    val chip_bg = Color(0xFFF2F4F7)
    val color_notification_unread_bg = Color(0xFFF5F5F5)
    val color_avatar_bg = Color(0xFFE0E0E0)

    // ============ BORDER ============
    val border_color = Color(0xFFE0E0E0)
    val stroke_color = Color(0xFFE0E0E0)
    val chip_border = Color(0xFFD0D5DD)
    val colorE5E7EB = Color(0xFFE5E7EB)

    // ============ SURFACE ============
    val on_surface = Color(0xFF1F1F1F)
    val ripple_color = Color(0xFF1A000000)
    val color7AFFFFFF = Color(0xFF7AFFFFFF)
    val colorBlack40 = Color(0xFF66000000)
    val blackAlpha60 = Color(0xFF99000000)

    // ============ PRIORITY ============
    val priority_normal = Color(0xFF2196F3)

    // ============ SHADOW ============
    val colorShadowGallery = Color(0xFF72A4BD)
    val colorShadowFlash = Color(0xFFBDA465)
    val colorA3815A = Color(0xFFA3815A)
    val colorF5F4EB = Color(0xFFF5F4EB)
    val noteTitleColor = Color(0xFFA3815A)

    // ============ GRADIENT ============
    val gradientStart = Color(0xFFDC96FF)
    val gradientEnd = Color(0xFFFF74CF)
}

// ============ EXTENSIONS ============
fun Color.toHexString(): String =
    String.format("#%08X", this.value.toLong())

fun Color.withAlpha(alpha: Float): Color =
    this.copy(alpha = alpha)
