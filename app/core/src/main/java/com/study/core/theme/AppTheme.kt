package com.study.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.study.core.theme.AppColor.Background
import com.study.core.theme.AppColor.DarkBackground
import com.study.core.theme.AppColor.DarkPrimary
import com.study.core.theme.AppColor.DarkSecondary
import com.study.core.theme.AppColor.Primary
import com.study.core.theme.AppColor.Secondary

private val LightColors = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background,
)

private val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
