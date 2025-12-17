package com.study.home.ui.home.components.header.streak

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.study.core.theme.AppDimen

@Composable
fun StreakBadge(streak: Int) {
    Row(
        modifier = Modifier
            .background(
                color = Color(0xFFFFEBEE),
                shape = RoundedCornerShape(AppDimen.dp_8)
            )
            .padding(AppDimen.dp_8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "🔥",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.width(AppDimen.dp_4))

        Text(
            text = streak.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
