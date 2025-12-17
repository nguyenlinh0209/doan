package com.study.core.theme.type

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {
    val typography = Typography(
        displayLarge = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        ),
        titleMedium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        bodyMedium = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    )
}
