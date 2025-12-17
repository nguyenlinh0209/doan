package com.study.home.ui.home.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.study.core.theme.AppColor
import com.study.core.theme.AppDimen
import com.study.core.theme.type.AppTypography
import com.study.home.ui.home.components.header.notifcation.NotificationIcon
import com.study.home.ui.home.components.header.streak.StreakBadge

@Composable
fun HeaderSection(
    userName: String,
    streak: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimen.dp_16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = com.study.resources.R.drawable.ic_person_white),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(AppDimen.dp_32)
                    .clip(CircleShape)
                    .background(AppColor.color75E073)
            )

            Spacer(modifier = Modifier.width(AppDimen.dp_12))

            Text(
                text = userName,
                style = AppTypography.typography.titleMedium,
                color = AppColor.black
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDimen.dp_12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StreakBadge(streak = streak)
            NotificationIcon()
        }
    }
}
